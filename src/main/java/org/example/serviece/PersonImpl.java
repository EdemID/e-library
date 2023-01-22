package org.example.serviece;

import org.example.model.Person;
import org.example.repository.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonImpl {

    private final PersonRepository repository;

    @Autowired
    public PersonImpl(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> foundPerson = repository.findById(id);
        return foundPerson.orElseThrow(RuntimeException::new);
    }

    public Person findByIdWithBooks(int id) {
        Optional<Person> foundPerson = repository.findById(id);
        Person person = null;
        if (foundPerson.isPresent()) {
            person = foundPerson.get();
            Hibernate.initialize(person.getBooks());
        }
        return person;
    }

    @Transactional
    public void save(Person person) {
        repository.save(person);
    }

    @Transactional
    public Person update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        return repository.save(updatedPerson);
    }

    @Transactional
    public void delete(Person person) {
        repository.delete(person);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public List<Person> findByName(String name) {
        return repository.findByName(name);
    }

}
