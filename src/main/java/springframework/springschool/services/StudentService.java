package springframework.springschool.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOs.DTOconverters.StudentDTOConverter;
import springframework.springschool.DTOs.StudentDTO;
import springframework.springschool.domain.Student;
import springframework.springschool.domain.Subject;
import springframework.springschool.exceptionhandler.exceptions.NoStudentsException;
import springframework.springschool.exceptionhandler.exceptions.StudentDeleteException;
import springframework.springschool.repository.StudentRepository;
import springframework.springschool.repository.SubjectRepository;
import springframework.springschool.services.request.CreateStudentRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final StudentDTOConverter studentDTOConverter;

    public List<StudentDTO> getStudentsByName(String name) {
        List<Student> students = studentRepository.findByNameStartingWith(name);
        if(students.isEmpty())
            throw new NoStudentsException();
        return studentDTOConverter.convertStudentToDTO(students);

    }

    public List<StudentDTO> getStudentsBySurname(String surname) {
        List<Student> students = studentRepository.findBySurnameStartingWith(surname);
        if(students.isEmpty())
            throw new NoStudentsException();
        return studentDTOConverter.convertStudentToDTO(students);
    }

    public List<StudentDTO> getStudentsByAgeHigherAndLower(int lower, int higher) {
        List<Student> students = studentRepository.findByAgeAfterAndAgeBefore(lower, higher);
        if(students.isEmpty())
            throw new NoStudentsException();
        return studentDTOConverter.convertStudentToDTO(students);
    }

    public List<StudentDTO> getStudents() {
        List<Student> students = studentRepository.findAll();
            if(students.isEmpty())
                throw new NoStudentsException();
        return studentDTOConverter.convertStudentToDTO(students);
    }

    public Student addNewStudent(CreateStudentRequest request) {
        List<Subject> subjectList = new ArrayList<>();

        List<String> listOfSubjectType = request.getSubjectTypeList();
        List<Long> listOfSubjectIds = request.getSubjectIdList();

        Student student = Student.builder()
                .surname(request.getSurname())
                .academicYear(request.getAcademicYear())
                .name(request.getName())
                .age(request.getAge())
                .build();

        if (listOfSubjectIds != null && !listOfSubjectIds.isEmpty())
            subjectList = subjectRepository.findAllById(listOfSubjectIds);


        if (listOfSubjectType != null && !listOfSubjectType.isEmpty()) {
            for (String subject : listOfSubjectType)
                subjectList.add(subjectRepository.findBySubjectType(subject));
        }

        student.setSubjectList(subjectList);

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id))
            throw new StudentDeleteException();
        studentRepository.deleteById(id);

    }

    public void addSubjectToStudent(Long id, String subjectType) {

        Optional<Student> studentOptional = studentRepository.findById(id);
        int i = 0;

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            if(student.getSubjectList() != null && !student.getSubjectList().isEmpty()) {
                for (Subject subject : student.getSubjectList())
                    if (subject.getSubjectType().equals(subjectType))
                        ++i;

                if (i == 0) {
                    student.getSubjectList().add(subjectRepository.findBySubjectType(subjectType));
                    studentRepository.save(student);
                }
            }
            else
                throw new NoStudentsException();

        }
    }

}