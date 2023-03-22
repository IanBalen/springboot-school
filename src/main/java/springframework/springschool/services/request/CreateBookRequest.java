package springframework.springschool.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springframework.springschool.domain.Person;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {

    private String nameOfBook;
    private String author;
    private int numOfBook;
    private List<Person> personList;

}
