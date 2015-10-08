package api.models.ner;

public class AnnotatedEntity {

    private String entity;
    private String type;
    int offsetBegin;
    int offsetEnd;

    public AnnotatedEntity(String entity, String type, int offsetBegin, int offsetEnd) {
        this.entity = entity;
        this.type = type;
        this.offsetBegin = offsetBegin;
        this.offsetEnd = offsetEnd;
    }

    public AnnotatedEntity(String entity, int offsetBegin, int offsetEnd) {
        this.entity = entity;
        this.offsetBegin = offsetBegin;
        this.offsetEnd = offsetEnd;
        type = null;
    }

    public String getEntity() {
        return entity;
    }

    public String getType() {
        return type;
    }

    public int getOffsetBegin() {
        return offsetBegin;
    }

    public int getOffsetEnd() {
        return offsetEnd;
    }

    @Override
    public String toString() {
        return "Entity: " + entity + ", Type: " + type;
    }
}
