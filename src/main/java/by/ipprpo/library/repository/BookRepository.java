package by.ipprpo.library.repository;

import by.ipprpo.library.model.Book;
import java.util.List;

public interface BookRepository {
    void save(Book book);
    Book findByTitle(String title);
    List<Book> findAll();
}