package com.alura.literalura.repository;

import com.alura.literalura.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByLanguageIgnoreCase(String language);

    long countByLanguageIgnoreCase(String language);

    List<Book> findByAuthor_NameContainingIgnoreCase(String authorName);
}