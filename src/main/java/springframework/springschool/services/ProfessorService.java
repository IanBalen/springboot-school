package springframework.springschool.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOs.DTOconverters.ProfessorDTOConverter;
import springframework.springschool.DTOs.ProfessorDTO;
import springframework.springschool.domain.Professor;
import springframework.springschool.domain.Subject;
import springframework.springschool.repository.ProfessorRepository;
import springframework.springschool.repository.SubjectRepository;
import springframework.springschool.services.request.CreateProfessorRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final ProfessorDTOConverter professorDTOConverter;


    public List<ProfessorDTO> getProfessors(){
        List<Professor> professors = professorRepository.findAll();
        return professorDTOConverter.convertProfessorToDTO(professors);
    }

    public List<ProfessorDTO> getProfessorsBySubject(String subject){
        List<Professor> professors = professorRepository.findBySubject(subject);
            return professorDTOConverter.convertProfessorToDTO(professors);
    }

    public void addNewProfessor(CreateProfessorRequest request){

        boolean hasId = Objects.nonNull(request.getSubjectId());
        boolean hasName = Objects.nonNull(request.getSubjectName());
        Professor professor = Professor.builder()
                .surname(request.getSurname())
                .name(request.getName())
                .age(request.getAge())
                .build();


        if(hasId) {
            Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());
            subject.ifPresent(professor::setSubject);
        }

        if(hasName) {
            Subject subject = subjectRepository.findBySubjectType(request.getSubjectName());
            professor.setSubject(subject);
        }
        
        professorRepository.save(professor);
    }

    public void editSubject(Long id, String subjectType) {

        Optional<Professor> professorOptional = professorRepository.findById(id);

        if(professorOptional.isPresent()){
            Professor professor = professorOptional.get();
            Subject subject = subjectRepository.findBySubjectType(subjectType);
            professor.setSubject(subject);
            professorRepository.save(professor);
        }
    }

    public void deleteProfessor(Long id){
        if(professorRepository.existsById(id))
            professorRepository.deleteById(id);
    }

}
