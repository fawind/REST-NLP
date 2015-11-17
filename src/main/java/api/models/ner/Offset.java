package api.models.ner;

public class Offset {

    private int offsetBegin;
    private int offsetEnd;

    public Offset(int offsetBegin, int offsetEnd) {
        this.offsetBegin = offsetBegin;
        this.offsetEnd = offsetEnd;
    }

    public int getOffsetBegin() {
        return offsetBegin;
    }

    public int getOffsetEnd() {
        return offsetEnd;
    }
}
