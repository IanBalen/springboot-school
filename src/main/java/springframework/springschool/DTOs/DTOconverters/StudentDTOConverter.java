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

    public List<StudentDTO> convertStudentToDTO(List<Student> students){

        List<StudentDTO> studentDTOList = new ArrayList<>();


        for(Student student : students){
            System.out.println(student.getSubjectList().size());
            StudentDTO studentDTO = StudentDTO
                    .builder()
                    .id(student.getId())
                    .academicYear(student.getAcademicYear())
                    .name(student.getName())
                    .surname(student.getSurname())
                    .age(student.getAge())
                    .subjectList(subjectDTOConverter.convertSubjectToDTOListWithoutList(student.getSubjectList()))
                    .build();
            //System.out.println(studentDTO);
            studentDTOList.add(studentDTO);
            //System.out.println(studentDTOList);
        }
        return studentDTOList;
    }

    public List<StudentDTO> convertStudentToDTOWithoutList(List<Student> studentList){

        List<StudentDTO> studentDTOList = new ArrayList<>();
        StudentDTO studentDTO;

        for(Student student : studentList){
            studentDTO = StudentDTO
                    .builder()
                    .id(student.getId())
                    .academicYear(student.getAcademicYear())
                    .name(student.getName())
                    .surname(student.getSurname())
                    .age(student.getAge())
                    .build();
            studentDTOList.add(studentDTO);
        }
        return studentDTOList;
    }


}
