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
    private String name;
    private String author;
    private int num;
    private boolean isBorrowed;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Person> personList;

}
