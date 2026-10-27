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

        library.issueBook("Война и мир", reader1);

        try {
            System.out.println("\nПопытка выдать ту же книгу другому читателю...");
            library.issueBook("Война и мир", reader2);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}