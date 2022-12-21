package springframework.springschool.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOs.DTOconverters.ProfessorDTOConverter;
import springframework.springschool.DTOs.ProfessorDTO;
import springframework.springschool.domain.Professor;
import springframework.springschool.domain.Subject;
import springframework.springschool.repository.ProfessorRepository;
import springframework.springschool.repository.SubjectRepository;
import springframework.springschool.services.request.CreateProfessorRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final ProfessorDTOConverter professorDTOConverter;


    public DataResult<List<ProfessorDTO>> getProfessors(){
        List<Professor> professors = professorRepository.findAll();
        return new DataResult<>(professorDTOConverter.convertProfessorToDTO(professors), HttpStatus.FOUND);
    }

    public DataResult<List<ProfessorDTO>> getProfessorsBySubject(String subject){
        List<Professor> professors = professorRepository.findBySubject(subject);
            return new DataResult<>(professorDTOConverter.convertProfessorToDTO(professors), HttpStatus.FOUND);
    }

    public ActionResult addNewProfessor(CreateProfessorRequest request){

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
            Optional<Subject> subject = subjectRepository.findBySubjectType(request.getSubjectName());
            subject.ifPresent(professor::setSubject);
        }
        
        professorRepository.save(professor);
        return new ActionResult("Professor has been added to the database", HttpStatus.CREATED);
    }

    public ActionResult editSubject(Long id, String subjectType) {

        Optional<Professor> professorOptional = professorRepository.findById(id);

        if(professorOptional.isPresent()){
            Professor professor = professorOptional.get();
            Optional<Subject> subject = subjectRepository.findBySubjectType(subjectType);
            subject.ifPresent(professor::setSubject);
            professorRepository.save(professor);
        }
        return new ActionResult("Professors subject has been updated", HttpStatus.ACCEPTED);

    }

    public ActionResult deleteProfessor(Long id){
        if(professorRepository.existsById(id))
            professorRepository.deleteById(id);
        return new ActionResult("Professor with id: " + id + " has been deleted", HttpStatus.ACCEPTED);
    }

}
