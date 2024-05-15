package online.book.store.repository.book;

import java.util.Optional;
import online.book.store.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @EntityGraph(attributePaths = {"categories"})
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"categories"})
    Page<Book> findAll(Specification<Book> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"categories"})
    Optional<Book> findById(Long id);

    @Query("FROM Book b JOIN FETCH b.categories c WHERE c.id = :categoryId")
    Page<Book> findBooksByCategoryId(Long categoryId, Pageable pageable);
}
