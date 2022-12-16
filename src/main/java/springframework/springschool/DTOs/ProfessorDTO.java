package springframework.springschool.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfessorDTO {

    private Long id;
    private String name;
    private String surname;
    private int age;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SubjectDTO subject;

}
