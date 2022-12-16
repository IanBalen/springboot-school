package springframework.springschool.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubjectRequest {
    @Pattern(regexp = "^([A-D])([0-9]{1,3})$", message = "Classroom name must start with capital letter A,B,C or D and be followed with 1-3 numbers")
    private String classroom;
    private String subjectType;
    private List<Long> professorList;
    private List<Long> studentList;


}
