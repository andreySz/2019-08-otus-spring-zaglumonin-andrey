package ru.otus.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

@ShellComponent
public class ShellCommands {

    private BookDao bookDao;
    private AuthorDao authorDao;
    private GenreDao genreDao;

    @Autowired
    public ShellCommands(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @ShellMethod(value = "Find book by id", key = {"fid", "find_by_id"})
    public void getById(Long bookId) throws SQLException {
        Book book = bookDao.getById(bookId);
        System.out.println(book.toString());
    }

    @ShellMethod(value = "Find all books by author_id", key = {"faid", "find_by_author_id"})
    public void getByAuthorId(Long authorId) {
        List<Book> booksList = bookDao.getBooksByAuthor(authorId);
        Iterator<Book> bookIterator = booksList.iterator();
        while (bookIterator.hasNext()) {
            System.out.println(bookIterator.next().toString());
        }
    }

    @ShellMethod(value = "Find all books by genre_id", key = {"fgid", "find_by_genre_id"})
    public void getByGenreId(Long genreId) {
        List<Book> booksList = bookDao.getBooksByGenre(genreId);
        Iterator<Book> bookIterator = booksList.iterator();
        while (bookIterator.hasNext()) {
            System.out.println(bookIterator.next().toString());
        }
    }

    @ShellMethod(value = "Insert new book", key = {"ab", "add_new_book"})
    public void addNewBook(String title, String author, String genre) {
        bookDao.insertBook(new Book(title, new Author(author), new Genre(genre)));
    }

    @ShellMethod(value = "Обновить название книги по id", key = {"u", "update"})
    public void updateBookTitleById(Long id, String title) {
        bookDao.updateBookTitleById(id, title);
    }

}
