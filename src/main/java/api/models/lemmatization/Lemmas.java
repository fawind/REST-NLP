package api.models.lemmatization;

import java.util.List;

public class Lemmas {

    private final String text;
    private final List<String> lemmas;

    public Lemmas(String text, List<String> lemmas) {
        this.text = text;
        this.lemmas = lemmas;
    }

    public String getText() {
        return text;
    }

    public List<String> getLemmas() {
        return lemmas;
    }

    @Override
    public String toString() {
        return lemmas.toString();
    }

}
