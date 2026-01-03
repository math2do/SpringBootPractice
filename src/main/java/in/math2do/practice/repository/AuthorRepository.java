package in.math2do.practice.repository;

import in.math2do.practice.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a JOIN FETCH a.books")
    public List<Author> findAllWithBooks();

    @Query("SELECT a FROM Author a JOIN FETCH a.books WHERE a.id = :id")
    public Optional<Author> findWithBooksById(Long id);
}
