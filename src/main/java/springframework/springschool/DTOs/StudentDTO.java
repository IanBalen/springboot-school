package springframework.springschool.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private int academicYear;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubjectDTO> subjectList;

}
