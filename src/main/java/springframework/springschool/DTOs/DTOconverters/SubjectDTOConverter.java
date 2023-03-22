package springframework.springschool.DTOs.DTOconverters;

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


    public List<SubjectDTO> convertSubjectToDTO(List<Subject> subjectsList, boolean hasStudent, boolean hasProfessor){

        List<SubjectDTO> subjectDTOList = new ArrayList<>();

        SubjectDTO subjectDTO;

        for(Subject subject : subjectsList){

            subjectDTO = convertSubjectToDTOWithoutList(subject);

            if(hasStudent)
                subjectDTO.setStudentList(studentDTOConverter.convertStudentToDTO(subject.getStudentList(), true));
            if(hasProfessor)
                subjectDTO.setProfessorList(professorDTOConverter.convertProfessorToDTO(subject.getProfessorList(), true));

            subjectDTOList.add(subjectDTO);
        }
        return subjectDTOList;
    }

    public SubjectDTO convertSubjectToDTOWithoutList(Subject subject){

        return SubjectDTO
                .builder()
                .subjectType(subject.getSubjectType())
                .id(subject.getId())
                .classroom(subject.getClassroom())
                .build();

    }



}
