package api.models;

public class Text {

    private final String text;

    public Text() {
        text = "";
    }

    public Text(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
