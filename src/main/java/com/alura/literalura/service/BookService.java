package com.alura.literalura.service;

import com.alura.literalura.dto.GutendexAuthorDto;
import com.alura.literalura.dto.GutendexBookDto;
import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final GutendexClientService gutendexClient;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(GutendexClientService gutendexClient,
                       BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.gutendexClient = gutendexClient;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Optional<Book> searchAndSaveBookByTitle(String title) {
        Optional<GutendexBookDto> maybe = gutendexClient.searchFirstBookByTitle(title);
        if (maybe.isEmpty()) return Optional.empty();

        GutendexBookDto dto = maybe.get();

        GutendexAuthorDto aDto = null;
        if (dto.getAuthors() != null && !dto.getAuthors().isEmpty()) {
            aDto = dto.getAuthors().get(0);
        }

        Author author = null;
        if (aDto != null) {
            author = authorRepository
                    .findByNameAndBirthYearAndDeathYear(aDto.getName(), aDto.getBirthYear(), aDto.getDeathYear())
                    .orElse(null);

            if (author == null) {
                author = new Author(aDto.getName(), aDto.getBirthYear(), aDto.getDeathYear());
                author = authorRepository.save(author);
            }
        }

        String language = null;
        if (dto.getLanguages() != null && !dto.getLanguages().isEmpty()) {
            language = dto.getLanguages().get(0);
        }

        Book book = new Book(dto.getTitle(), language, dto.getDownloadCount(), author);
        book = bookRepository.save(book);
        return Optional.of(book);
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> listBooksByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }

    public long countBooksByLanguage(String language) {
        return bookRepository.countByLanguage(language);
    }
}