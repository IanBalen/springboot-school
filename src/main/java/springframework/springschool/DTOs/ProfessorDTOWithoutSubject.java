package springframework.springschool.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfessorDTOWithoutSubject {

    private Long id;
    private String name;
    private String surname;
    private int age;

}
