package org.example.serviece;

import org.example.models.Person;
import org.example.repositorie.PersonRepository;
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

    public Person findById(Integer id) {
        Optional<Person> foundPerson = repository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        repository.save(person);
    }

    @Transactional
    public Person update(Person updatedPerson) {

        return repository.save(updatedPerson);
    }

    @Transactional
    public void delete(Person person) {
        repository.delete(person);
    }

    public List<Person> findByName(String name) {
        return repository.findByName(name);
    }

}
