package springframework.springschool.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOconverters.SubjectDTOConverter;
import springframework.springschool.DTOs.SubjectDTO;
import springframework.springschool.domain.Professor;
import springframework.springschool.domain.Student;
import springframework.springschool.domain.Subject;
import springframework.springschool.repository.ProfessorRepository;
import springframework.springschool.repository.StudentRepository;
import springframework.springschool.repository.SubjectRepository;
import springframework.springschool.services.request.CreateSubjectRequest;

import java.util.ArrayList;
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

            System.out.println("ne postoji");

            List<Student> studentList = new ArrayList<>();
            List<Professor> professorList = new ArrayList<>();

            List<Long> listOfStudentsById = request.getStudentList();
            List<Long> listOfProfessorsById = request.getProfessorList();

            System.out.println(listOfStudentsById.size());

            Subject subject = Subject
                    .builder()
                    .classroom(request.getClassroom())
                    .subjectType(request.getSubjectType())
                    .build();

            if (listOfStudentsById != null && !listOfStudentsById.isEmpty()) {
                studentList = studentRepository.findAllById(listOfStudentsById);

                for (Student student : studentList)
                    student.getSubjectList().add(subject);

                subject.setStudentList(studentList);
                System.out.println(studentList.size());
                studentRepository.saveAll(studentList);

            }
                if (!listOfProfessorsById.isEmpty() && listOfProfessorsById != null) {
                    professorList = professorRepository.findAllById(listOfProfessorsById);

                    for (Professor professor : professorList)
                        professor.setSubject(subject);

                    subject.setProfessorList(professorList);
                    professorRepository.saveAll(professorList);


                }

            subjectRepository.save(subject);
        }



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
        SubjectDTO subjectDTO = subjectDTOConverter.convertSubjectToDTOWithoutList(subject);

        return subjectDTO;
    }

}
