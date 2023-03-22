package springframework.springschool.DTOs.DTOconverters;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import springframework.springschool.DTOs.ProfessorDTO;
import springframework.springschool.domain.Professor;
import springframework.springschool.domain.Subject;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfessorDTOConverter {

    private final SubjectDTOConverter subjectDTOConverter;

    public ProfessorDTOConverter(@Lazy SubjectDTOConverter subjectDTOConverter) {
        this.subjectDTOConverter = subjectDTOConverter;
    }

    public List<ProfessorDTO> convertProfessorToDTO(List<Professor> professorList, boolean hasSubject){

        ProfessorDTO professorDTO;
        List<ProfessorDTO> listOfProfessorDTO = new ArrayList<>();

        for(Professor professor : professorList){

            Subject subject = professor.getSubject();

            professorDTO = ProfessorDTO
                    .builder()
                    .id(professor.getId())
                    .age(professor.getAge())
                    .lastName(professor.getLastName())
                    .firstName(professor.getFirstName())
                    .build();

            if(subject != null && hasSubject)
                professorDTO.setSubject(subjectDTOConverter.convertSubjectToDTOWithoutList(subject));


            listOfProfessorDTO.add(professorDTO);
        }

        return listOfProfessorDTO;
    }

}
