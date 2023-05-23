package springframework.springschool.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOs.DTOconverters.SubjectDTOConverter;
import springframework.springschool.DTOs.SubjectDTO;
import springframework.springschool.domain.Subject;
import springframework.springschool.exceptionhandler.BadRequestException;
import springframework.springschool.repository.SubjectRepository;
import springframework.springschool.services.request.CreateSubjectRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private  final SubjectRepository subjectRepository;

    private final SubjectDTOConverter subjectDTOConverter;

    public DataResult<List<SubjectDTO>> getSubjects(){
        List<Subject> subjectList =  subjectRepository.findAll();
        return new DataResult<>(subjectDTOConverter.convertSubjectToDTO(subjectList, true, true), HttpStatus.FOUND);
    }

    public ActionResult createSubject(CreateSubjectRequest request){

        if(!subjectRepository.existsBySubjectType(request.getSubjectType())) {

            Subject subject = Subject
                    .builder()
                    .classroom(request.getClassroom())
                    .subjectType(request.getSubjectType())
                    .build();


            subjectRepository.save(subject);

           return new ActionResult("Subject has been added to the database", HttpStatus.CREATED);
        }

        else
            throw new BadRequestException("Subject already exists");

    }

    public ActionResult deleteSubject(Long id){
        if(!subjectRepository.existsById(id))
            throw new BadRequestException("Subject does not exist");

        subjectRepository.deleteById(id);
        return new ActionResult("Subject has been deleted", HttpStatus.OK);
    }

    public DataResult<List<SubjectDTO>> findByClassroom(String classroom){
        List<Subject> subjectList = subjectRepository.findByClassroom(classroom);
        return new DataResult<>(subjectDTOConverter.convertSubjectToDTO(subjectList, true, true), HttpStatus.FOUND);
    }

    public DataResult<List<SubjectDTO>> findBySubjectType(String subjectType){

        Optional<Subject> subject = subjectRepository.findBySubjectType(subjectType);

        if(subject.isEmpty())
            throw new BadRequestException("Subject does not exist");

        List<SubjectDTO> list = new ArrayList<>();
        list.add(subjectDTOConverter.convertSubjectToDTOWithoutList(subject.get()));

        return new DataResult<>(list, HttpStatus.FOUND);
    }

}
