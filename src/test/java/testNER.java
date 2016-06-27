import api.controllers.NERController;
import api.controllers.TokenizerController;
import api.models.Text;
import api.models.ner.AnnotatedEntity;
import api.models.ner.AnnotatedText;
import modules.NamedEntityRecognizer;
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

import java.util.List;

public class testNER {

    private static NamedEntityRecognizer ner = new NamedEntityRecognizer();
    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new NERController()).build();
    }

    @Test
    public void testSimpleSentence() {
        String text = "Barack Hussein Obama II is the 44th and current President of the United States, and the first " +
            "African American to hold the office";
        int entityCount = 2;

        AnnotatedText annotatedText = ner.classify(text);
        List<AnnotatedEntity> entities = annotatedText.getEntities();
        assertEquals(entityCount, entities.size());
    }

    @Test
    public void testComplexSentence() {
        String text = "The Hasso Plattner Institute, shortly HPI, is a German information technology university " +
            "college, affiliated to the University of Potsdam and is located in Potsdam-Babelsberg nearby Berlin.";
        int entityCount = 5;

        AnnotatedText annotatedText = ner.classify(text);
        List<AnnotatedEntity> entities = annotatedText.getEntities();
        assertEquals(entityCount, entities.size());
    }

    @Test
    public void testEmptySentence() {
        String text = "";
        int entityCount = 0;

        AnnotatedText annotatedText = ner.classify(text);
        List<AnnotatedEntity> entities = annotatedText.getEntities();
        assertEquals(entityCount, entities.size());
    }

    @Test
    public void testGermanSentence() {
        String text = "Angela Dorothea Merkel ist eine deutsche Politikerin (CDU) und seit dem 22. November 2005 " +
                "Bundeskanzlerin der Bundesrepublik Deutschland.";
        int entityCount = 4;

        AnnotatedText annotatedText = ner.classifyGerman(text);
        List<AnnotatedEntity> entities = annotatedText.getEntities();
        assertEquals(entityCount, entities.size());
    }

    @Test
    public void testTextRequest() throws Exception {
        Text text = new Text("The Hasso Plattner Institute, shortly HPI, is a German information technology university " +
            "college, affiliated to the University of Potsdam and is located in Potsdam-Babelsberg nearby Berlin.");
        int entityCount = 5;
        mvc.perform(post("/api/ner")
            .contentType(JsonUtil.APPLICATION_JSON_UTF8)
            .content(JsonUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.*", hasSize(2)))
            .andExpect(jsonPath("$.data.text", is(text.getText())))
            .andExpect(jsonPath("$.data.entities", hasSize(entityCount)));
    }

    @Test
    public void testEmptyRequest() throws Exception {
        mvc.perform(post("/api/ner")
            .contentType(JsonUtil.APPLICATION_JSON_UTF8)
            .content("{}"))
            .andExpect(status().isBadRequest());
    }


}
