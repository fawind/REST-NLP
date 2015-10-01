package api.models.response;

import api.models.response.ErrorResource;

public class ErrorWrapper {

    private ErrorResource error;

    public ErrorWrapper(int errorCode, String errorMessage) {
        error = new ErrorResource(errorCode, errorMessage);
    }

    public ErrorWrapper(ErrorResource error) {
        this.error = error;
    }

    public ErrorResource getError() {
        return error;
    }


}
