package springframework.springschool.services.results;

import org.springframework.http.HttpStatus;

public class DataResult<T> extends ActionResult{

    private T data;
    public DataResult(T data) {
        this.data = data;
    }

    public DataResult(T data, String message, HttpStatus httpStatus) {
        super(message, httpStatus);
        this.data = data;
    }

    public DataResult(T data, HttpStatus httpStatus) {
        super(httpStatus);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
