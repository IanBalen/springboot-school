package springframework.springschool.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfessorDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SubjectDTO subject;

}
