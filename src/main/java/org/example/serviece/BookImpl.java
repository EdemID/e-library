package org.example.serviece;

import org.example.models.Book;
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

    public Book findById(Integer id) {
        Optional<Book> foundBook = repository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        repository.save(book);
    }

    @Transactional
    public Book update(Book updatedBook) {

        return repository.save(updatedBook);
    }

    @Transactional
    public void delete(Book book) {
        repository.delete(book);
    }
}
