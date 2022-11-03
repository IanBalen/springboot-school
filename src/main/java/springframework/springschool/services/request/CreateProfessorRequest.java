package springframework.springschool.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProfessorRequest {

    private String name;
    private String surname;
    private int age;
    private Long subjectId;
    private String subjectName;

}
