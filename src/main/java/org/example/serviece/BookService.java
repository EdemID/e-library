package org.example.serviece;

import org.example.exception.IncorrectParametersException;
import org.example.model.Book;
import org.example.model.Person;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(boolean isSortByYear) {
        if (isSortByYear) {
            return repository.findAll(Sort.by("yearOfPublication", "bookName"));
        }
        return repository.findAll();
    }

    public List<Book> findAll(Integer page, int booksPerPage, boolean isSortByYear) throws IncorrectParametersException {
        if (isSortByYear) {
            return repository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearOfPublication", "bookName"))).getContent();
        } else {
            return repository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public List<Book> findByBookNameStartsWith(String startsWith) {
        if (startsWith.equals("")) {
            return null;
        } else {
            return repository.findByBookNameStartsWith(startsWith);
        }
    }

    public Book findById(int id) {
        Optional<Book> foundBook = repository.findById(id);
        return foundBook.orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void save(Book book) {
        repository.save(book);
    }

    @Transactional
    public Book update(int id, Book updatedBook) {
        Book bookToBeUpdated = repository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner()); // чтобы не терялась связь
        return repository.save(updatedBook);
    }

    @Transactional
    public void delete(Book book) {
        repository.delete(book);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Transactional
    public void assignBook(int id, Person person) {
        Book book = findById(id);
        book.setOwner(person);
        book.setTookAt(new Date());

        save(book);
    }

    @Transactional
    public void returnBook(int id) {
        repository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTookAt(null);
                    book.setExpired(false);
                });
//  save - не нужен, так как book лежит в persistence context и Hibernate знает об этом book, поэтому сам обновит сущность в бд при каждом вызове setter
//        save(book);
    }
}
