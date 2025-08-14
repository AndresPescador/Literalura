package com.alura.literalura.service;

import com.alura.literalura.entity.Author;
import com.alura.literalura.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository repository) {
        this.authorRepository = repository;
    }

    public List<Author> listAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> listAuthorsAliveInYear(Integer year) {
        return authorRepository.findAuthorsAliveInYear(year);
    }

    public List<Author> findAuthorsBornAfter(int year) {
        return authorRepository.findByBirthYearGreaterThan(year);
    }

    public List<Author> findAuthorsDiedBefore(int year) {
        return authorRepository.findByDeathYearLessThan(year);
    }
}