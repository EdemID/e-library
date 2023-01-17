package org.example.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    private String bookName;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 1, message = "Name must be at least 2 character")
    private String author;

    @Column(name = "year")
    @Temporal(TemporalType.DATE)
    //  TODO: создать спринг-валидатор для формата даты,
    //  чтобы пользователю выходила красивая ошибка, если ввел неверно дату,
    //  иначе спринг выдает свою ошибку
    @DateTimeFormat(pattern = "dd/MM/yyyy") // дд/мм/гггг
    private Date yearOfPublication;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;


    public Book() {
    }

    public Book(int id, String bookName, String author, Date yearOfPublication, Person owner) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String name) {
        this.bookName = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Date year) {
        this.yearOfPublication = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", year=" + yearOfPublication +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && yearOfPublication == book.yearOfPublication && Objects.equals(bookName, book.bookName) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookName, author, yearOfPublication);
    }
}
