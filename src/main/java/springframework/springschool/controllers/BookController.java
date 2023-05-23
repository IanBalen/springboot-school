package springframework.springschool.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springframework.springschool.DTOs.BookDTO;
import springframework.springschool.services.BookService;
import springframework.springschool.services.request.CreateBookRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<DataResult<List<BookDTO>>> getBooks(){
        return ResponseEntity.ok(bookService.getBooks());
    }

    @PostMapping
    public ResponseEntity<ActionResult> createBook(@RequestBody CreateBookRequest bookRequest) {
        return ResponseEntity.ok(bookService.createBook(bookRequest));
    }



}
