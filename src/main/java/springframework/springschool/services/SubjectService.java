package springframework.springschool.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOs.DTOconverters.SubjectDTOConverter;
import springframework.springschool.DTOs.SubjectDTO;
import springframework.springschool.domain.Professor;
import springframework.springschool.domain.Student;
import springframework.springschool.domain.Subject;
import springframework.springschool.exceptionhandler.exceptions.NoProfessorException;
import springframework.springschool.exceptionhandler.exceptions.NoStudentsException;
import springframework.springschool.exceptionhandler.exceptions.NoSubjectExists;
import springframework.springschool.exceptionhandler.exceptions.SubjectExistsException;
import springframework.springschool.repository.ProfessorRepository;
import springframework.springschool.repository.StudentRepository;
import springframework.springschool.repository.SubjectRepository;
import springframework.springschool.services.request.CreateSubjectRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubjectService {

    private  final SubjectRepository subjectRepository;

    private final ProfessorRepository professorRepository;

    private final StudentRepository studentRepository;
    private final SubjectDTOConverter subjectDTOConverter;




    public DataResult<List<SubjectDTO>> getSubjects(){
        List<Subject> subjectList =  subjectRepository.findAll();
        return new DataResult<>(subjectDTOConverter.convertSubjectToDTOList(subjectList), HttpStatus.FOUND);
    }

    public ActionResult addNewSubject(CreateSubjectRequest request){

        if(!subjectRepository.existsBySubjectType(request.getSubjectType())) {

            List<Student> studentList;
            List<Professor> professorList;

            List<Long> listOfStudentsById = request.getStudentList();
            List<Long> listOfProfessorsById = request.getProfessorList();

            for(long id: listOfStudentsById) {
                Optional<Student> student = studentRepository.findById(id);
                if(student.isEmpty())
                    throw new NoStudentsException();
            }

            for(long id : listOfProfessorsById){
                Optional<Professor> professor = professorRepository.findById(id);
                if(professor.isEmpty())
                    throw new NoProfessorException();
            }


            Subject subject = Subject
                    .builder()
                    .classroom(request.getClassroom())
                    .subjectType(request.getSubjectType())
                    .build();

            if (!listOfStudentsById.isEmpty()) {
                studentList = studentRepository.findAllById(listOfStudentsById);

                subject.setStudentList(studentList);

                studentRepository.saveAll(studentList);

            }
                if (!listOfProfessorsById.isEmpty()) {
                    professorList = professorRepository.findAllById(listOfProfessorsById);

                    subject.setProfessorList(professorList);

                    professorRepository.saveAll(professorList);


                }


            subjectRepository.save(subject);

           return new ActionResult("Subject has been added to the database", HttpStatus.CREATED);
        }

        else
            throw new SubjectExistsException();

    }

    public ActionResult deleteSubject(Long id){
        if(!subjectRepository.existsById(id))
            throw new SubjectExistsException();

        subjectRepository.deleteById(id);
        return new ActionResult("Subject has been deleted", HttpStatus.OK);
    }

    public DataResult<List<SubjectDTO>> findByClassroom(String classroom){
        List<Subject> subjectList = subjectRepository.findByClassroom(classroom);
        return new DataResult<>(subjectDTOConverter.convertSubjectToDTOList(subjectList), HttpStatus.FOUND);
    }

    public DataResult<List<SubjectDTO>> findBySubjectType(String subjectType){

        Optional<Subject> subject = subjectRepository.findBySubjectType(subjectType);

        if(subject.isEmpty())
            throw new NoSubjectExists();

        List<SubjectDTO> list = new ArrayList<>();
        list.add(subjectDTOConverter.convertSubjectToDTOWithoutList(subject.get()));

        return new DataResult<>(list, HttpStatus.FOUND);
    }

}
