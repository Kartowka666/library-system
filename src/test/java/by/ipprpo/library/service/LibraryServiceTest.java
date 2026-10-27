package by.ipprpo.library.service;

import by.ipprpo.library.model.Book;
import by.ipprpo.library.model.Reader;
import by.ipprpo.library.repository.BookRepository;
import by.ipprpo.library.repository.InMemoryBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryServiceTest {
    private LibraryService library;
    private BookRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryBookRepository();
        library = new LibraryService(repository);
    }

    @Test
    public void testIssueBookSuccess() {
        Book book = new Book("Чистый код", "Мартин");
        library.addBook(book);
        Reader reader = new Reader("Алексей");

        assertDoesNotThrow(() -> library.issueBook("Чистый код", reader));
        assertTrue(book.isIssued());
    }

    @Test
    public void testIssueBookNotFound() {
        Reader reader = new Reader("Алексей");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            library.issueBook("Несуществующая книга", reader);
        });

        assertEquals("Книга не найдена: Несуществующая книга", exception.getMessage());
    }

    @Test
    public void testIssueBookAlreadyIssued() {
        Book book = new Book("Гарри Поттер", "Роулинг");
        library.addBook(book);

        Reader reader1 = new Reader("Иван");
        Reader reader2 = new Reader("Петр");

        library.issueBook("Гарри Поттер", reader1);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            library.issueBook("Гарри Поттер", reader2);
        });

        assertTrue(exception.getMessage().contains("уже находится на руках"));
    }

 
    @Test
    public void testAddBookWithBlankTitleThrows() {
        Book book = new Book("   ", "Автор");

        assertThrows(IllegalArgumentException.class, () -> library.addBook(book));
    }
}