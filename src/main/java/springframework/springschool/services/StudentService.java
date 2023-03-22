package springframework.springschool.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOs.DTOconverters.StudentDTOConverter;
import springframework.springschool.DTOs.StudentDTO;
import springframework.springschool.domain.Book;
import springframework.springschool.domain.Student;
import springframework.springschool.domain.Subject;
import springframework.springschool.exceptionhandler.BadRequestException;
import springframework.springschool.repository.BookRepository;
import springframework.springschool.repository.StudentRepository;
import springframework.springschool.repository.SubjectRepository;
import springframework.springschool.services.request.CreateStudentRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final BookRepository bookRepository;
    private final StudentDTOConverter studentDTOConverter;

    public DataResult<List<StudentDTO>> getStudentsByName(String firstName) {
        List<Student> students = studentRepository.findByFirstNameStartingWith(firstName);
        if(students.isEmpty())
            throw new BadRequestException("No students found with this name");
        return new DataResult<>(studentDTOConverter.convertStudentToDTO(students, true), HttpStatus.FOUND);

    }

    public DataResult<List<StudentDTO>> getStudentsBySurname(String lastName) {
        List<Student> students = studentRepository.findByLastNameStartingWith(lastName);
        if(students.isEmpty())
            throw new BadRequestException("No students found with this name");
        return new DataResult<>(studentDTOConverter.convertStudentToDTO(students, true), HttpStatus.FOUND);
    }

    public DataResult<List<StudentDTO>> getStudentsByAcademicYear(int year){
        List<Student> students = studentRepository.findByAcademicYear(year);
        if(students.isEmpty())
            throw new BadRequestException("No students found with this name");
        return new DataResult<>(studentDTOConverter.convertStudentToDTO(students, true), HttpStatus.FOUND);
    }

    public DataResult<List<StudentDTO>> getStudentsByAgeHigherAndLower(int lower, int higher) {
        List<Student> students = studentRepository.findByAgeAfterAndAgeBefore(lower, higher);
        if(students.isEmpty())
            throw new BadRequestException("No students found with this name");
        return new DataResult<>(studentDTOConverter.convertStudentToDTO(students, true), HttpStatus.FOUND);
    }

    public DataResult<List<StudentDTO>> getStudents() {
        List<Student> students = studentRepository.findAll();
            if(students.isEmpty())
                throw new BadRequestException("No students found with this name");
        return new DataResult<>(studentDTOConverter.convertStudentToDTO(students, true), HttpStatus.FOUND);
    }

    public ActionResult addNewStudent(CreateStudentRequest request) {
        List<Subject> subjectList = new ArrayList<>();

        List<String> listOfSubjectType = request.getSubjectTypeList();

        Student student = Student.builder()
                .lastName(request.getLastName())
                .academicYear(request.getAcademicYear())
                .firstName(request.getFirstName())
                .age(request.getAge())
                .build();

        if (listOfSubjectType != null && !listOfSubjectType.isEmpty()) {
            for (String subject : listOfSubjectType) {
                Optional<Subject> subjectOptional = subjectRepository.findBySubjectType(subject);
                if(subjectOptional.isPresent())
                    subjectList.add(subjectOptional.get());
                else
                    throw new BadRequestException("Subject with type: " + subject + " does not exist in the database");
            }
        }

        student.setSubjectList(subjectList);

        studentRepository.save(student);

        return new ActionResult("Student has been added to the database", HttpStatus.CREATED);
    }

    public ActionResult deleteStudent(Long id) {
        if (!studentRepository.existsById(id))
            throw new BadRequestException("No student found with this name");

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
                throw new BadRequestException("No students found with this name");

    }

    public ActionResult assignBookToStudent(Long id, String bookName){

        Optional<Student> studentOptional = studentRepository.findById(id);
        List<Book> bookList = bookRepository.findBookByNameOfBook(bookName);

        if(studentOptional.isPresent() && !bookList.isEmpty()){

            Student student = studentOptional.get();

            List<Book> studentBookList = student.getBookList();

            for(Book b : studentBookList)
                if(b.getNameOfBook().equals(bookName))
                    throw new BadRequestException("Student already has this book");

            boolean bookAssigned = false;

            for(Book b : bookList)
                if(!b.isBorrowed()){
                    student.getBookList().add(b);
                    b.setBorrowed(true);
                    bookRepository.save(b);
                    bookAssigned = true;
                    break;
                }

            if(!bookAssigned)
                throw new BadRequestException("All books with this name are borrowed");

            studentRepository.save(student);

            return new ActionResult("Book \"" + bookName + "\" has been borrowed to student.", HttpStatus.ACCEPTED);
        }

        else if(studentOptional.isEmpty())
            throw new BadRequestException("No students found with this name");

        else
            throw new BadRequestException("No books found with this name");
    }

    public ActionResult unassignBookFromStudent(Long studentId, Long bookId){

        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if(optionalStudent.isPresent() && optionalBook.isPresent()){

            Student student = optionalStudent.get();
            Book book = optionalBook.get();

            for(Book b : student.getBookList())
                if(Objects.equals(b.getId(), bookId)){
                    student.getBookList().remove(b);
                    break;
                }

            book.setBorrowed(false);

            studentRepository.save(student);
            bookRepository.save(book);

            return new ActionResult("\"" + book.getNameOfBook() + "\" has been removed from student.", HttpStatus.ACCEPTED);
        }

        else if(optionalStudent.isEmpty())
            throw new BadRequestException("No students found with this name");

        else
            throw new BadRequestException("No books found with this name");
    }

}