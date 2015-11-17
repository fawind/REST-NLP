package api.models.ner;

import java.util.ArrayList;
import java.util.List;

public class AnnotatedEntity {

    private String entity;
    private String type;
    private List<Offset> offsets;

    public AnnotatedEntity(String entity, String type, List<Offset> offsets) {
        this.entity = entity;
        this.type = type;
        this.offsets = offsets;
    }

    public AnnotatedEntity(String entity, String type, Offset offset) {
        this.entity = entity;
        this.type = type;
        this.offsets = new ArrayList<>();
        this.offsets.add(offset);
    }

    public String getEntity() {
        return entity;
    }

    public String getType() {
        return type;
    }

    public List<Offset> getOffsets() {
        return offsets;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void addOffset(Offset offset) {
        offsets.add(offset);
    }

    @Override
    public String toString() {
        return "Entity: " + entity + ", Type: " + type;
    }
}
