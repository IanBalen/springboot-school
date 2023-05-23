package springframework.springschool.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOs.DTOconverters.ProfessorDTOConverter;
import springframework.springschool.DTOs.ProfessorDTO;
import springframework.springschool.domain.Book;
import springframework.springschool.domain.Professor;
import springframework.springschool.domain.Subject;
import springframework.springschool.exceptionhandler.BadRequestException;
import springframework.springschool.repository.BookRepository;
import springframework.springschool.repository.ProfessorRepository;
import springframework.springschool.repository.SubjectRepository;
import springframework.springschool.services.request.CreateProfessorRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;
    private final BookRepository bookRepository;
    private final ProfessorDTOConverter professorDTOConverter;


    public DataResult<List<ProfessorDTO>> getProfessors(){
        List<Professor> professors = professorRepository.findAll();
        if(professors.isEmpty())
            throw new BadRequestException("No professors found");
        else
            return new DataResult<>(professorDTOConverter.convertProfessorToDTO(professors, true), HttpStatus.FOUND);
    }

    public DataResult<List<ProfessorDTO>> getProfessorsBySubject(String subject){
        List<Professor> professors = professorRepository.findBySubject(subject);
        if(professors.isEmpty())
            throw new BadRequestException("No professors found with this subject");
        else
            return new DataResult<>(professorDTOConverter.convertProfessorToDTO(professors, true), HttpStatus.FOUND);
    }

    public ActionResult createProfessor(CreateProfessorRequest request){

        boolean hasName = Objects.nonNull(request.getSubjectName());

        Professor professor = Professor.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .age(request.getAge())
                .build();


        if(hasName) {
            Optional<Subject> subject = subjectRepository.findBySubjectType(request.getSubjectName());
            if(subject.isPresent())
                professor.setSubject(subject.get());
            else
                throw new BadRequestException("No subject found with this name");
        }
        
        professorRepository.save(professor);
        return new ActionResult("Professor has been added to the database", HttpStatus.CREATED);
    }

    public ActionResult updateSubject(Long id, String subjectType) {

        Optional<Professor> professorOptional = professorRepository.findById(id);

        if(professorOptional.isPresent()){
            Professor professor = professorOptional.get();
            Optional<Subject> subject = subjectRepository.findBySubjectType(subjectType);
            subject.ifPresent(professor::setSubject);
            professorRepository.save(professor);
            return new ActionResult("Professors subject has been updated", HttpStatus.ACCEPTED);
        }
        
        else 
            throw new BadRequestException("No professor found with this id");

    }

    public ActionResult deleteProfessor(Long id){
        if(professorRepository.existsById(id)) {
            professorRepository.deleteById(id);
            return new ActionResult("Professor with id: " + id + " has been deleted", HttpStatus.ACCEPTED);
        }
        else 
            throw new BadRequestException("No professor found with this id");
    }

    public ActionResult assignBookToProfessor(Long id, String bookName){

        Optional<Professor> professorOptional = professorRepository.findById(id);
        Optional<Book> bookOptional = bookRepository.findByNameAndBorrowedFalse(bookName);

        if(professorOptional.isPresent() && bookOptional.isPresent()){

            Professor professor = professorOptional.get();
            Book book = bookOptional.get();

            List<Book> professorBookList = professor.getBookList();

            for(Book b : professorBookList)
                if(b.getName().equals(bookName))
                    throw new BadRequestException("Professor already has this book.");

            professorBookList.add(book);
            book.setBorrowed(true);

            bookRepository.save(book);
            professorRepository.save(professor);

            return new ActionResult("Book \"" + bookName + "\" has been borrowed to professor.", HttpStatus.ACCEPTED);
        }

        else if(professorOptional.isEmpty())
            throw new BadRequestException("Professor with id: " + id + " does not exist.");

        else
            throw new BadRequestException("Book with name: " + bookName + " does not exist.");
    }

    public ActionResult unassignBookFromProfessor(Long professorId, Long bookId){

        Optional<Professor> optionalProfessor = professorRepository.findById(professorId);
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if(optionalProfessor.isPresent() && optionalBook.isPresent()){

            Professor student = optionalProfessor.get();
            Book book = optionalBook.get();

            for(Book b : student.getBookList())
                if(Objects.equals(b.getId(), bookId)){
                    student.getBookList().remove(b);
                    break;
                }

            book.setBorrowed(false);

            professorRepository.save(student);
            bookRepository.save(book);

            return new ActionResult("\"" + book.getName() + "\" has been removed from professor.", HttpStatus.ACCEPTED);
        }

        else if(optionalProfessor.isEmpty())
            throw new BadRequestException("Professor with id: " + professorId + " does not exist.");

        else
            throw new BadRequestException("Book with id: " + bookId + " does not exist.");
    }

}
