package in.math2do.practice.service;

import in.math2do.practice.entity.Author;
import in.math2do.practice.entity.Book;
import in.math2do.practice.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
class TestExamples {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void readAuthorWithBooks() {
        Author author = authorRepository.findWithBooksById(1L).orElseThrow(() -> new RuntimeException("Author not found"));
        log.info("Author: {}", author);
        log.info("Books: {}", author.getBooks().stream().map(Book::getTitle).collect(Collectors.joining(",")));
    }

    @Test
    void readAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        authors.forEach(author -> {
            log.info("Author: {}", author.getFirstName() + " " + author.getLastName());
        });
    }
}
