package springframework.springschool.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOs.DTOconverters.SubjectDTOConverter;
import springframework.springschool.DTOs.SubjectDTO;
import springframework.springschool.domain.Professor;
import springframework.springschool.domain.Student;
import springframework.springschool.domain.Subject;
import springframework.springschool.exceptionhandler.exceptions.SubjectExistsException;
import springframework.springschool.repository.ProfessorRepository;
import springframework.springschool.repository.StudentRepository;
import springframework.springschool.repository.SubjectRepository;
import springframework.springschool.services.request.CreateSubjectRequest;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectService {

    private  final SubjectRepository subjectRepository;

    private final ProfessorRepository professorRepository;

    private final StudentRepository studentRepository;
    private final SubjectDTOConverter subjectDTOConverter;




    public List<SubjectDTO> getSubjects(){
        List<Subject> subjectList =  subjectRepository.findAll();
        return subjectDTOConverter.convertSubjectToDTOList(subjectList);
    }

    public void addNewSubject(CreateSubjectRequest request){

        if(!subjectRepository.existsBySubjectType(request.getSubjectType())) {

            List<Student> studentList;
            List<Professor> professorList;

            List<Long> listOfStudentsById = request.getStudentList();
            List<Long> listOfProfessorsById = request.getProfessorList();


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
        }

        else
            throw new SubjectExistsException();

    }

    public void deleteSubject(Long id){
        if(subjectRepository.existsById(id))
            subjectRepository.deleteById(id);
    }

    public List<SubjectDTO> findByClassroom(String classroom){
        List<Subject> subjectList = subjectRepository.findByClassroom(classroom);
        return subjectDTOConverter.convertSubjectToDTOList(subjectList);
    }

    public SubjectDTO findBySubjectType(String subjectType){

        Subject subject = subjectRepository.findBySubjectType(subjectType);

        return subjectDTOConverter.convertSubjectToDTOWithoutList(subject);
    }

}
