package springframework.springschool.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import springframework.springschool.DTOs.BookDTO;
import springframework.springschool.DTOs.DTOconverters.BookDTOConverter;
import springframework.springschool.domain.Book;
import springframework.springschool.exceptionhandler.BadRequestException;
import springframework.springschool.repository.BookRepository;
import springframework.springschool.services.request.CreateBookRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookDTOConverter bookDTOConverter;

    public DataResult<List<BookDTO>> getBooks() {
        List<Book> bookList = bookRepository.findAll();

        if(bookList.isEmpty())
            throw new BadRequestException("No books found in the database");

        return new DataResult<>(bookDTOConverter.convertBookToDTO(bookList), HttpStatus.FOUND);
    }

    public ActionResult AddBook(CreateBookRequest request){

        List<Book> bookList = bookRepository.findBookByNameOfBook(request.getNameOfBook());

            Book book = Book.builder()
                    .numOfBook(request.getNumOfBook())
                    .nameOfBook(request.getNameOfBook())
                    .personList(request.getPersonList())
                    .author(request.getAuthor())
                    .build();

            for(Book b : bookList)
                b.incrementNumOfBook(request.getNumOfBook());


        bookRepository.save(book);

        return new ActionResult("Book has been added to the database", HttpStatus.CREATED);

    }

}
