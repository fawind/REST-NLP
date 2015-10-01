package api.models.response;

public class ErrorResource {

    private int code;
    private String message;

    public ErrorResource(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
