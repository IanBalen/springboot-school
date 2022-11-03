package springframework.springschool.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubjectRequest {

    private String classroom;
    private String subjectType;
    private List<Long> professorList;
    private List<Long> studentList;


}
