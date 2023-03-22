package springframework.springschool.services.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springframework.springschool.services.validation.ValidCreateStudentRequest;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidCreateStudentRequest
public class CreateStudentRequest {

    private String firstName;
    private String lastName;
    private int age;
    private int academicYear;
    private List<String> subjectTypeList;

}
