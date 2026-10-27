package by.ipprpo.library.service;

import by.ipprpo.library.model.Book;
import by.ipprpo.library.model.Reader;
import by.ipprpo.library.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;

public class LibraryService {
    private final BookRepository repository;

    // Dependency Injection через конструктор
    public LibraryService(BookRepository repository) {
        this.repository = repository;
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Книга не может быть null");
        }
        if (StringUtils.isBlank(book.getTitle())) {
            throw new IllegalArgumentException("Название книги не может быть пустым");
        }
        if (StringUtils.isBlank(book.getAuthor())) {
            throw new IllegalArgumentException("Автор книги не может быть пустым");
        }
        repository.save(book);
    }

    public void issueBook(String title, Reader reader) {
        if (StringUtils.isBlank(title)) {
            throw new IllegalArgumentException("Название книги не может быть пустым");
        }
        if (reader == null) {
            throw new IllegalArgumentException("Читатель не может быть null");
        }

        Book book = repository.findByTitle(title);
        if (book == null) {
            throw new IllegalArgumentException("Книга не найдена: " + title);
        }

        // Индивидуальное задание: книга не может быть выдана, если уже на руках
        if (book.isIssued()) {
            throw new IllegalStateException("Книга '" + title + "' уже находится на руках!");
        }

        book.setIssued(true);
        System.out.println("Успех: Книга '" + title + "' выдана читателю " + reader.getName());
    }
}