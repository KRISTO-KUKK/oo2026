package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.entity.Book;
import ee.mihkel.veebipood.repository.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class BookController {

    private final BookRepository bookRepository;
    private final RestClient restClient = RestClient.create();

    @Value("${bibles.api-url}")
    private String biblesApiUrl;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("books")
    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @PostMapping("books")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PostMapping("books/batch")
    public List<Book> addBooks(@RequestBody List<Book> books) {
        return bookRepository.saveAll(books);
    }

    @GetMapping("bibles")
    public List<Map<String, Object>> getBibles() {
        List<Map<String, Object>> bibles = restClient.get()
                .uri(biblesApiUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        if (bibles == null) {
            return List.of();
        }
        return bibles;
    }
}
