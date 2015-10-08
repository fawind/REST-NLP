package api.models.ner;

import java.util.List;

public class AnnotatedText {

    String text;
    List<AnnotatedEntity> entities;

    public AnnotatedText(String text, List<AnnotatedEntity> entities) {
        this.text = text;
        this.entities = entities;
    }

    public String getText() {
        return text;
    }

    public List<AnnotatedEntity> getEntities() {
        return entities;
    }

    @Override
    public String toString() {
        return text + " | Entities: " + entities;
    }
}
