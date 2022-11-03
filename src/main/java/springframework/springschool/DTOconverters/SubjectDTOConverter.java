package springframework.springschool.DTOconverters;

import org.springframework.stereotype.Component;
import springframework.springschool.DTOs.SubjectDTO;
import springframework.springschool.domain.Subject;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectDTOConverter {

    private final StudentDTOConverter studentDTOConverter;
    private final ProfessorDTOConverter professorDTOConverter;

    public SubjectDTOConverter(StudentDTOConverter studentDTOConverter, ProfessorDTOConverter professorDTOConverter) {
        this.studentDTOConverter = studentDTOConverter;
        this.professorDTOConverter = professorDTOConverter;
    }


    public List<SubjectDTO> convertSubjectToDTOList(List<Subject> subjectsList){

        List<SubjectDTO> subjectDTOList = new ArrayList<>();

        SubjectDTO subjectDTO;

        for(Subject subject : subjectsList){

            subjectDTO = SubjectDTO
                    .builder()
                    .subjectType(subject.getSubjectType())
                    .id(subject.getId())
                    .professorList(professorDTOConverter.convertProfessorToDTOWithoutSubject(subject.getProfessorList()))
                    .studentList(studentDTOConverter.convertStudentToDTOWithoutList(subject.getStudentList()))
                    .classroom(subject.getClassroom())
                    .build();

            subjectDTOList.add(subjectDTO);
        }
        return subjectDTOList;
    }

    public SubjectDTO convertSubjectToDTO(Subject subject){

        return SubjectDTO
                .builder()
                .subjectType(subject.getSubjectType())
                .studentList(studentDTOConverter.convertStudentToDTOWithoutList(subject.getStudentList()))
                .professorList(professorDTOConverter.convertProfessorToDTOWithoutSubject(subject.getProfessorList()))
                .id(subject.getId())
                .classroom(subject.getClassroom())
                .build();

    }

    public SubjectDTO convertSubjectToDTOWithoutList(Subject subject){

        return SubjectDTO
                .builder()
                .subjectType(subject.getSubjectType())
                .id(subject.getId())
                .classroom(subject.getClassroom())
                .build();

    }


    public List<SubjectDTO> convertSubjectToDTOListWithoutList(List<Subject> subjectsList){

        List<SubjectDTO> subjectDTOList = new ArrayList<>();


        for(Subject subject : subjectsList){
            System.out.println(subject.getSubjectType());
            SubjectDTO subjectDTO = SubjectDTO
                    .builder()
                    .subjectType(subject.getSubjectType())
                    .id(subject.getId())
                    .classroom(subject.getClassroom())
                    .build();

            subjectDTOList.add(subjectDTO);
        }
        return subjectDTOList;
    }


}
