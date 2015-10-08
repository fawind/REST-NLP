package api.models.tokenizer;

import java.util.List;

public class Tokens {

    private final String text;
    private final List<String> tokens;

    public Tokens(String text, List<String> tokens) {
        this.text = text;
        this.tokens = tokens;
    }

    public String getText() {
        return text;
    }

    public List<String> getTokens() {
        return tokens;
    }

    @Override
    public String toString() {
        return tokens.toString();
    }
}
