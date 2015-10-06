import api.controllers.TokenizerController;
import api.models.Text;
import modules.Tokenizer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import util.JsonUtil;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.*;

import java.util.List;

public class testTokenizer {

    private Tokenizer tokenizer;
    private MockMvc mvc;

    private class WrongRequest {
        private String test;
        public WrongRequest(String test) { this.test = test; }
        public String getTest() {  return test; }
    }

    @Before
    public void setUp() {
        tokenizer = new Tokenizer();
        mvc = MockMvcBuilders.standaloneSetup(new TokenizerController()).build();
    }

    @Test
    public void testSimpleWords() {
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi porttitor.";
        int tokenCount = 13;
        List<String> tokens = tokenizer.tokenizeWords(text);
        assertEquals(tokenCount, tokens.size());
    }

    @Test
    public void testSimpleSentences() {
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi porttitor.";
        int tokenCount = 2;
        List<String> tokens = tokenizer.tokenizeSentences(text);
        assertEquals(tokenCount, tokens.size());
    }

    @Test
    public void testEmptyString() {
        String text = "";
        int tokenCount = 0;
        List<String> tokens = tokenizer.tokenizeSentences(text);
        assertEquals(tokenCount, tokens.size());
    }

    @Test
    public void testSimpleTokensRequest() throws Exception {
        Text text = new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi porttitor.");
        int tokenCount = 13;
        mvc.perform(post("/api/tokenize/tokens")
            .contentType(JsonUtil.APPLICATION_JSON_UTF8)
            .content(JsonUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.*", hasSize(2)))
            .andExpect(jsonPath("$.data.text", is(text.getText())))
            .andExpect(jsonPath("$.data.tokens", hasSize(tokenCount)));
    }

    @Test
    public void testEmptyTextRequest() throws Exception {
        Text text = new Text("");
        int tokenCount = 0;
        mvc.perform(post("/api/tokenize/tokens")
            .contentType(JsonUtil.APPLICATION_JSON_UTF8)
            .content(JsonUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.*", hasSize(2)))
            .andExpect(jsonPath("$.data.text", is(text.getText())))
            .andExpect(jsonPath("$.data.tokens", hasSize(tokenCount)));
    }

    @Test
    public void testSimpleWordsRequest() throws Exception {
        Text text = new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi porttitor.");
        int tokenCount = 2;
        mvc.perform(post("/api/tokenize/words")
            .contentType(JsonUtil.APPLICATION_JSON_UTF8)
            .content(JsonUtil.convertObjectToJsonBytes(text)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.*", hasSize(2)))
            .andExpect(jsonPath("$.data.text", is(text.getText())))
            .andExpect(jsonPath("$.data.tokens", hasSize(tokenCount)));
    }

    @Test
    public void testWrongRequest() throws Exception {
        WrongRequest request = new WrongRequest("test");
        mvc.perform(post("/api/tokenize/tokens")
            .contentType(JsonUtil.APPLICATION_JSON_UTF8)
            .content(JsonUtil.convertObjectToJsonBytes(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testEmptyRequest() throws Exception {
        mvc.perform(post("/api/tokenize/tokens")
            .contentType(JsonUtil.APPLICATION_JSON_UTF8)
            .content("{}"))
            .andExpect(status().isBadRequest());
    }

}
