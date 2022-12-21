package springframework.springschool.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOs.DTOconverters.StudentDTOConverter;
import springframework.springschool.DTOs.StudentDTO;
import springframework.springschool.domain.Student;
import springframework.springschool.domain.Subject;
import springframework.springschool.exceptionhandler.exceptions.NoStudentsException;
import springframework.springschool.exceptionhandler.exceptions.NoSubjectExists;
import springframework.springschool.exceptionhandler.exceptions.StudentDeleteException;
import springframework.springschool.repository.StudentRepository;
import springframework.springschool.repository.SubjectRepository;
import springframework.springschool.services.request.CreateStudentRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final StudentDTOConverter studentDTOConverter;

    public DataResult<List<StudentDTO>> getStudentsByName(String name) {
        List<Student> students = studentRepository.findByNameStartingWith(name);
        if(students.isEmpty())
            throw new NoStudentsException();
        return new DataResult<>(studentDTOConverter.convertStudentToDTO(students), HttpStatus.FOUND);

    }

    public DataResult<List<StudentDTO>> getStudentsBySurname(String surname) {
        List<Student> students = studentRepository.findBySurnameStartingWith(surname);
        if(students.isEmpty())
            throw new NoStudentsException();
        return new DataResult<>(studentDTOConverter.convertStudentToDTO(students), HttpStatus.FOUND);
    }

    public DataResult<List<StudentDTO>> getStudentsByAgeHigherAndLower(int lower, int higher) {
        List<Student> students = studentRepository.findByAgeAfterAndAgeBefore(lower, higher);
        if(students.isEmpty())
            throw new NoStudentsException();
        return new DataResult<>(studentDTOConverter.convertStudentToDTO(students), HttpStatus.FOUND);
    }

    public DataResult<List<StudentDTO>> getStudents() {
        List<Student> students = studentRepository.findAll();
            if(students.isEmpty())
                throw new NoStudentsException();
        return new DataResult<>(studentDTOConverter.convertStudentToDTO(students), HttpStatus.FOUND);
    }

    public ActionResult addNewStudent(CreateStudentRequest request) {
        List<Subject> subjectList = new ArrayList<>();

        List<String> listOfSubjectType = request.getSubjectTypeList();
        List<Long> listOfSubjectIds = request.getSubjectIdList();

        Student student = Student.builder()
                .surname(request.getSurname())
                .academicYear(request.getAcademicYear())
                .name(request.getName())
                .age(request.getAge())
                .build();

        if (listOfSubjectIds != null && !listOfSubjectIds.isEmpty()) {
            for (long id : listOfSubjectIds) {
                Optional<Subject> subject = subjectRepository.findById(id);
                if (subject.isEmpty())
                    throw new NoSubjectExists();
            }
            subjectList = subjectRepository.findAllById(listOfSubjectIds);
        }


        if (listOfSubjectType != null && !listOfSubjectType.isEmpty()) {
            for (String subject : listOfSubjectType) {
                Optional<Subject> subjectOptional = subjectRepository.findBySubjectType(subject);
                if(subjectOptional.isPresent())
                    subjectList.add(subjectOptional.get());
                else
                    throw new NoSubjectExists();
            }
        }

        student.setSubjectList(subjectList);

        studentRepository.save(student);

        return new ActionResult("Student has been added to the database", HttpStatus.CREATED);
    }

    public ActionResult deleteStudent(Long id) {
        if (!studentRepository.existsById(id))
            throw new StudentDeleteException();
        studentRepository.deleteById(id);
        return new ActionResult("Student with id: " + id + " has been deleted", HttpStatus.OK);

    }

    public ActionResult addSubjectToStudent(Long id, String subjectType) {

        Optional<Student> studentOptional = studentRepository.findById(id);
        int i = 0;

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            if (student.getSubjectList() != null && !student.getSubjectList().isEmpty()) {
                 for (Subject subject : student.getSubjectList())
                    if (subject.getSubjectType().equals(subjectType))
                        ++i;

                if (i == 0) {
                    Optional<Subject> subjectOptional = subjectRepository.findBySubjectType(subjectType);
                    if (subjectOptional.isPresent()) {
                        student.getSubjectList().add(subjectOptional.get());
                        studentRepository.save(student);
                    }
                }
            }
            return new ActionResult("Subject has been added to student", HttpStatus.ACCEPTED);
        }
            else
                throw new NoStudentsException();


    }

}