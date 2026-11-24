package by.ipprpo.library.service;






import by.ipprpo.library.model.Book;
import by.ipprpo.library.model.Reader;
import by.ipprpo.library.repository.BookRepository;
import by.ipprpo.library.repository.InMemoryBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        // ИСПРАВЛЕНО: сообщение ДОЛЖНО содержать "уже находится на руках"
        assertTrue(exception.getMessage().contains("уже находится на руках"));
    }

    @Test
    public void testAddBookWithBlankAuthorThrows() {
        Book book = new Book("Война и мир", "   ");
        assertThrows(IllegalArgumentException.class, () -> library.addBook(book));
    }

    @Test
    public void testReturnBookSuccess() {
        Book book = new Book("Преступление и наказание", "Достоевский");
        library.addBook(book);
        Reader reader = new Reader("Сергей");

        library.issueBook("Преступление и наказание", reader);
        assertTrue(book.isIssued());

        assertDoesNotThrow(() -> library.returnBook("Преступление и наказание"));
        assertFalse(book.isIssued());
    }

    @Test
    public void testReturnBookNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            library.returnBook("Несуществующая книга");
        });

        assertEquals("Книга не найдена: Несуществующая книга", exception.getMessage());
    }

    @Test
    public void testReturnBookNotIssued() {
        Book book = new Book("Мастер и Маргарита", "Булгаков");
        library.addBook(book);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            library.returnBook("Мастер и Маргарита");
        });

        assertTrue(exception.getMessage().contains("не находится на руках"));
    }
}