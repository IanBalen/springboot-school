package springframework.springschool.services.results;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionResult {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonIgnore
    private HttpStatus httpStatus;

    public ActionResult(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
