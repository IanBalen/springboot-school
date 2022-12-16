package springframework.springschool.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SubjectDTO {

    private Long id;
    private String classroom;
    private String subjectType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProfessorDTO> professorList;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<StudentDTO> studentList;

}
