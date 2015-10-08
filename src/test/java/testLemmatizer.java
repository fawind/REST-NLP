import api.controllers.LemmatizationController;
import api.models.Words;
import modules.Lemmatizer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import util.JsonUtil;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testLemmatizer {

    private Lemmatizer lemmatizer;
    private MockMvc mvc;

    private class WrongRequest {
        private String test;
        public WrongRequest(String test) { this.test = test; }
        public String getTest() {  return test; }
    }

    @Before
    public void setUp() {
        lemmatizer = new Lemmatizer();
        mvc = MockMvcBuilders.standaloneSetup(new LemmatizationController()).build();
    }

    @Test
    public void testSimpleText() {
        String text = "Lemmatization usually refers to doing things properly with the use of a vocabulary and " +
            "morphological analysis of words, normally aiming to remove inflectional endings only and to return the" +
            "base or dictionary form of a word, which is known as the lemma.";
        int lemmaCount = 44;
        List<String> lemmas = lemmatizer.lemmatize(text);
        assertEquals(lemmaCount, lemmas.size());
    }

    @Test
    public void testEmptyText() {
        String text = "";
        int lemmaCount = 0;
        List<String> lemmas = lemmatizer.lemmatize(text);
        assertEquals(lemmaCount, lemmas.size());
    }

    @Test
    public void testList() {
        String text = "Lemmatization, usually, refers, to, doing, things, properly, with, the, use, of, a, vocabulary, " +
            "and, morphological, analysis, of, words, normally, aiming, to, remove, inflectional, endings, only, " +
            "and, to, return, the, base, or, dictionary, form, of, a, word, which, is, known, as, the, lemma";
        List<String> words = new ArrayList<String>(Arrays.asList(text.split(", ")));
        int lemmaCount = words.size();
        List<String> lemmas = lemmatizer.lemmatize(words);
        assertEquals(lemmaCount, lemmas.size());
    }

    @Test
    public void testSimpleLemmaRequest() throws Exception{
        String text = "Lemmatization, usually, refers, to, doing, things, properly, with, the, use, of, a, vocabulary, " +
            "and, morphological, analysis, of, words, normally, aiming, to, remove, inflectional, endings, only, " +
            "and, to, return, the, base, or, dictionary, form, of, a, word, which, is, known, as, the, lemma";
        Words words = new Words(new ArrayList<>(Arrays.asList(text.split(", "))));
        int lemmaCount = words.getWords().size();
        mvc.perform(post("/api/lemmatize")
            .contentType(JsonUtil.APPLICATION_JSON_UTF8)
            .content(JsonUtil.convertObjectToJsonBytes(words)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.*", hasSize(1)))
            .andExpect(jsonPath("$.data.lemmas", hasSize(lemmaCount)));
    }

    @Test
    public void testWrongRequest() throws Exception {
        WrongRequest request = new WrongRequest("test");
        mvc.perform(post("/api/lemmatize")
            .contentType(JsonUtil.APPLICATION_JSON_UTF8)
            .content(JsonUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());
    }
}
