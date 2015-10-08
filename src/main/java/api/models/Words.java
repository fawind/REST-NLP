package api.models;

import java.util.List;

public class Words {

    private List<String> words;

    public Words() {
        words = null;
    }

    public Words(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }
}
