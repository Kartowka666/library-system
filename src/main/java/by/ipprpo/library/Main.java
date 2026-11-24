package by.ipprpo.library;

import by.ipprpo.library.model.Book;
import by.ipprpo.library.model.Reader;
import by.ipprpo.library.repository.BookRepository;
import by.ipprpo.library.repository.InMemoryBookRepository;
import by.ipprpo.library.service.LibraryService;

public class Main {
    public static void main(String[] args) {
        BookRepository repository = new InMemoryBookRepository();
        LibraryService library = new LibraryService(repository);

        library.addBook(new Book("Война и мир", "Толстой"));
        library.addBook(new Book("1984", "Оруэлл"));

        Reader reader1 = new Reader("Иван");
        Reader reader2 = new Reader("Петр");

        // Сценарий 1 (INFO): успешная выдача
        library.issueBook("Война и мир", reader1);

        // Сценарий 2 (WARN): книга уже на руках
        try {
            library.issueBook("Война и мир", reader2);
        } catch (Exception ignored) {
            // уже залогировано в сервисе
        }

        // Сценарий 3 (INFO): успешный возврат
        library.returnBook("Война и мир");

        // Сценарий 4 (INFO): повторная выдача после возврата
        library.issueBook("Война и мир", reader2);

        // Сценарий 5 (WARN): возврат книги, которая не выдана
        try {
            library.returnBook("1984");
        } catch (Exception ignored) {
            // уже залогировано в сервисе
        }

        // Сценарий 6 (WARN): выдача несуществующей книги
        try {
            library.issueBook("Несуществующая книга", reader1);
        } catch (Exception ignored) {
            // уже залогировано в сервисе
        }

        // Сценарий 7 (ERROR): null-читатель
        try {
            library.issueBook("1984", null);
        } catch (Exception ignored) {
            // уже залогировано в сервисе
        }
    }
}