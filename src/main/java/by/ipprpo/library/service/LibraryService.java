package by.ipprpo.library.service;

import by.ipprpo.library.model.Book;
import by.ipprpo.library.model.Reader;
import by.ipprpo.library.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryService {
    private static final Logger log = LoggerFactory.getLogger(LibraryService.class);

    private final BookRepository repository;

    public LibraryService(BookRepository repository) {
        this.repository = repository;
    }

    public void addBook(Book book) {
        if (book == null) {
            log.error("Ошибка добавления: книга не может быть null");
            throw new IllegalArgumentException("Книга не может быть null");
        }
        if (StringUtils.isBlank(book.getTitle())) {
            log.error("Ошибка добавления: пустое название книги");
            throw new IllegalArgumentException("Название книги не может быть пустым");
        }
        if (StringUtils.isBlank(book.getAuthor())) {
            log.error("Ошибка добавления: пустой автор книги");
            throw new IllegalArgumentException("Автор книги не может быть пустым");
        }
        repository.save(book);
        log.info("Книга добавлена в библиотеку: '{}', автор {}",
                book.getTitle(), book.getAuthor());
    }

    public void issueBook(String title, Reader reader) {
        log.info("Запрос на выдачу книги: '{}', читатель {}",
                title, reader != null ? reader.getName() : "null");

        if (StringUtils.isBlank(title)) {
            log.error("Ошибка выдачи: пустое название книги");
            throw new IllegalArgumentException("Название книги не может быть пустым");
        }
        if (reader == null) {
            log.error("Ошибка выдачи: читатель не указан");
            throw new IllegalArgumentException("Читатель не может быть null");
        }

        Book book = repository.findByTitle(title);
        if (book == null) {
            log.warn("Попытка выдать недоступную книгу: '{}' не найдена в библиотеке", title);
            throw new IllegalArgumentException("Книга не найдена: " + title);
        }

        if (book.isIssued()) {
            log.warn("Попытка выдать книгу '{}', которая уже находится на руках", title);
            throw new IllegalStateException("Книга '" + title + "' уже находится на руках!");
        }

        book.setIssued(true);
        log.info("Книга '{}' успешно выдана читателю {}", title, reader.getName());
    }

    public void returnBook(String title) {
        log.info("Запрос на возврат книги: '{}'", title);

        if (StringUtils.isBlank(title)) {
            log.error("Ошибка возврата: пустое название книги");
            throw new IllegalArgumentException("Название книги не может быть пустым");
        }

        Book book = repository.findByTitle(title);
        if (book == null) {
            log.warn("Попытка вернуть недоступную книгу: '{}' не найдена в библиотеке", title);
            throw new IllegalArgumentException("Книга не найдена: " + title);
        }

        if (!book.isIssued()) {
            log.warn("Попытка вернуть книгу '{}', которая не была выдана", title);
            throw new IllegalStateException("Книга '" + title + "' не находится на руках!");
        }

        book.setIssued(false);
        log.info("Книга '{}' успешно возвращена", title);
    }
}