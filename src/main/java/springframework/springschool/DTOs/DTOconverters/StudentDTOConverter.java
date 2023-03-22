package springframework.springschool.DTOs.DTOconverters;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import springframework.springschool.DTOs.StudentDTO;
import springframework.springschool.domain.Student;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDTOConverter {


    private final SubjectDTOConverter subjectDTOConverter;

    public StudentDTOConverter(@Lazy SubjectDTOConverter subjectDTOConverter) {
        this.subjectDTOConverter = subjectDTOConverter;
    }

    public List<StudentDTO> convertStudentToDTO(List<Student> students, boolean hasSubject){

        List<StudentDTO> studentDTOList = new ArrayList<>();


        for(Student student : students){
            StudentDTO studentDTO = StudentDTO
                    .builder()
                    .id(student.getId())
                    .academicYear(student.getAcademicYear())
                    .firstName(student.getFirstName())
                    .lastName(student.getLastName())
                    .age(student.getAge())
                    .build();

            if(hasSubject)
                studentDTO.setSubjectList(subjectDTOConverter.convertSubjectToDTO(student.getSubjectList(), false, false));

            studentDTOList.add(studentDTO);
        }
        return studentDTOList;
    }


}
