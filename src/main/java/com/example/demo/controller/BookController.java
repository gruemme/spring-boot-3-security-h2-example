package com.example.demo.controller;

import com.example.demo.dto.BookInput;
import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(path = "/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping(path = "/books/{id}")
    public Book bookById(@PathVariable Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + " not available");
        }

        return bookOptional.get();
    }

    @PostMapping(path = "/books")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "BasicAuth")
    public Book createBook(@RequestBody BookInput newBookInput) {
        Book newBook = new Book();
        newBook.setTitle(newBookInput.getTitle());

        return bookRepository.save(newBook);
    }
}
