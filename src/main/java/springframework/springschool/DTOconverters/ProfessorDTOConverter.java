package springframework.springschool.DTOconverters;

import org.springframework.stereotype.Component;
import springframework.springschool.DTOs.ProfessorDTOWithoutSubject;
import springframework.springschool.domain.Professor;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfessorDTOConverter {

    public List<ProfessorDTOWithoutSubject> convertProfessorToDTOWithoutSubject(List<Professor> professorList){

        ProfessorDTOWithoutSubject professorDTOWithoutSubject;
        List<ProfessorDTOWithoutSubject> listOfProfessorDTOWithoutSubject = new ArrayList<>();

        for(Professor professor : professorList){

            professorDTOWithoutSubject = ProfessorDTOWithoutSubject
                    .builder()
                    .id(professor.getId())
                    .age(professor.getAge())
                    .surname(professor.getSurname())
                    .name(professor.getName())
                    .build();

            listOfProfessorDTOWithoutSubject.add(professorDTOWithoutSubject);
        }

        return listOfProfessorDTOWithoutSubject;
    }

}
