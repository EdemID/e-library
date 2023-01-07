package org.example.dao;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;
    private final BookDAO bookDAO;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate, BookDAO bookDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookDAO = bookDAO;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        Person person = jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
        person.setBooks(jdbcTemplate.query("select * from person join book on person.person_id = book.person_id where book.person_id = ?;", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)));

        return person;
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, age) VALUES(?, ?)", person.getName(), person.getAge());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE person_id=?", updatedPerson.getName(),
                updatedPerson.getAge(), id);
    }

    public void delete(int id) {
        bookDAO.deleteReference(id);
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }
}
