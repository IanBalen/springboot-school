package springframework.springschool.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springframework.springschool.services.validation.ValidCreateProfessorRequest;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidCreateProfessorRequest
public class CreateProfessorRequest {

    private String firstName;
    private String lastName;
    private int age;
    private String subjectName;

}
