package springframework.springschool.services.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springframework.springschool.services.validation.ValidUpdateStudentRequest;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ValidUpdateStudentRequest
public class UpdateStudentRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private int academicYear;

}
