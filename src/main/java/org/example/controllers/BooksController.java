package org.example.controllers;

import org.example.models.Book;
import org.example.models.Person;
import org.example.serviece.BookImpl;
import org.example.serviece.PersonImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookImpl bookService;
    private final PersonImpl personService;

    public BooksController(BookImpl bookService, PersonImpl personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @ModelAttribute("person") Person person) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("people", personService.findAll());
        model.addAttribute("namePerson", book.getOwner());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String assignBook(@ModelAttribute("person")Person person,
                             @PathVariable("id") int id) {
        bookService.assignBook(id, person);
        return "redirect:/books/{id}";
    }

    @GetMapping("/{id}/return")
    public String returnBook(@PathVariable("id") int id) {
        bookService.returnBook(id);

        return "redirect:/books/{id}";
    }
}
