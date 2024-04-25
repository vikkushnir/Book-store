package online.book.store.repository;

import java.util.List;
import java.util.Optional;
import online.book.store.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
