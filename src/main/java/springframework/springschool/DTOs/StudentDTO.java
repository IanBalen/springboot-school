package springframework.springschool.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentDTO {

    private Long id;
    private String name;
    private String surname;
    private int age;
    private int academicYear;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubjectDTO> subjectList;

}
