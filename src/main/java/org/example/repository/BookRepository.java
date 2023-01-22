package org.example.repository;

import org.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByBookName(String name);

    List<Book> findByAuthor(String author);

    List<Book> findByBookNameStartingWith(String startingWith);

    List<Book> findByBookNameStartsWith(String startsWith);

}
