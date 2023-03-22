package springframework.springschool.DTOs.DTOconverters;

import org.springframework.stereotype.Component;
import springframework.springschool.DTOs.BookDTO;
import springframework.springschool.domain.Book;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookDTOConverter {

    public List<BookDTO> convertBookToDTO(List<Book> books){

        BookDTO bookDTO;
        List<BookDTO> dtoList = new ArrayList<>();

        for(Book book : books){
            bookDTO = BookDTO.builder()
                    .id(book.getId())
                    .nameOfBook(book.getNameOfBook())
                    .numOfBook(book.getNumOfBook())
                    .author(book.getAuthor())
                    //.personList(book.getPersonList())
                    .isBorrowed(book.isBorrowed())
                    .build();
            dtoList.add(bookDTO);
        }

        return dtoList;

    }

}
