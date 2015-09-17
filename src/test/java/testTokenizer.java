import modules.Tokenizer;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class testTokenizer {
    @Test
    public void testSimpleWords() {
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi porttitor.";
        int tokenCount = 13;
        Tokenizer tokenizer = new Tokenizer();
        List<String> tokens = tokenizer.tokenizeWords(text);

        assertEquals(tokenCount, tokens.size());
    }

    @Test
    public void testSimpleSentences() {
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi porttitor.";
        int tokenCount = 2;
        Tokenizer tokenizer = new Tokenizer();
        List<String> tokens = tokenizer.tokenizeSentences(text);

        assertEquals(tokenCount, tokens.size());
    }


}
