package springframework.springschool.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProfessorRequest {

    @Pattern(regexp = "^([^0-9]*)$")
    private String name;
    @Pattern(regexp = "^([^0-9]*)$")
    private String surname;
    @Min(25)
    @Max(65)
    private int age;
    private Long subjectId;
    private String subjectName;

}
