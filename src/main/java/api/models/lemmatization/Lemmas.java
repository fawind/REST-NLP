package api.models.lemmatization;

import java.util.List;

public class Lemmas {

    private final List<String> lemmas;

    public Lemmas(List<String> lemmas) {
        this.lemmas = lemmas;
    }

    public List<String> getLemmas() {
        return lemmas;
    }

    @Override
    public String toString() {
        return lemmas.toString();
    }

}
