package springframework.springschool.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import springframework.springschool.domain.Person;

import java.util.List;

@Data
@Builder
public class BookDTO {

    private Long id;
    private String nameOfBook;
    private String author;
    private int numOfBook;
    private boolean isBorrowed;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Person> personList;

}
