package api.models.response;

import api.models.response.ErrorResource;

public class ResponseWrapper<T> {

    private T data;
    private ErrorResource error;

    public ResponseWrapper(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

}
