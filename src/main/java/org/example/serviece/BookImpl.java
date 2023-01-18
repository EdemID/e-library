package org.example.serviece;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookImpl {

    private final BookRepository repository;

    @Autowired
    public BookImpl(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll() {
        return repository.findAll();
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
        updatedBook.setId(id);
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
        System.out.println(book.getOwner());
        save(book);
    }

    @Transactional
    public void returnBook(int id) {
        Book book = findById(id);
        book.setOwner(null);

        save(book);
    }
}
