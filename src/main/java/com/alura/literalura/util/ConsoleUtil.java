package com.alura.literalura.util;

import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
import com.alura.literalura.service.AuthorService;
import com.alura.literalura.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUtil {

    private final Scanner sc;
    private final BookService bookService;
    private final AuthorService authorService;

    public ConsoleUtil(Scanner sc, BookService bookService, AuthorService authorService) {
        this.sc = sc;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public void startMenuLoop() {
        boolean running = true;
        while (running) {
            printMenu();
            String input = sc.nextLine().trim();
            switch (input) {
                case "1":
                    searchBookByTitle();
                    break;
                case "2":
                    listAllBooks();
                    break;
                case "3":
                    listAllAuthors();
                    break;
                case "4":
                    listAuthorsAliveInYear();
                    break;
                case "5":
                    listBooksByLanguage();
                    break;
                case "0":
                    running = false;
                    System.out.println("Saliendo... ¡hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("Elija la opción a través de su número:");
        System.out.println("1- buscar libro por título");
        System.out.println("2- listar libros registrados");
        System.out.println("3- listar autores registrados");
        System.out.println("4- listar autores vivos en un determinado año");
        System.out.println("5- listar libros por idioma");
        System.out.println("0- salir");
        System.out.print("Ingrese una opción: ");
    }

    private void searchBookByTitle() {
        System.out.print("Ingrese el nombre del libro que desea buscar: ");
        String title = sc.nextLine().trim();
        if (title.isBlank()) {
            System.out.println("Título vacío. Operación cancelada.");
            return;
        }
        Optional<Book> saved = bookService.searchAndSaveBookByTitle(title);
        if (saved.isPresent()) {
            System.out.println("Libro encontrado y guardado:");
            System.out.println(saved.get());
        } else {
            System.out.println("No se encontró ningún libro para el título indicado.");
        }
    }

    private void listAllBooks() {
        List<Book> books = bookService.listAllBooks();
        if (books.isEmpty()) {
            System.out.println("No hay libros registrados aún.");
            return;
        }
        System.out.println("Libros registrados:");
        books.forEach(b -> System.out.println(b));
    }

    private void listAllAuthors() {
        List<Author> authors = authorService.listAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados aún.");
            return;
        }
        System.out.println("Autores registrados:");
        authors.forEach(a -> System.out.println(a));
    }

    private void listAuthorsAliveInYear() {
        System.out.print("Ingrese el año para filtrar autores vivos en ese año (ej: 1900): ");
        String line = sc.nextLine().trim();
        try {
            Integer year = Integer.valueOf(line);
            List<Author> alive = authorService.listAuthorsAliveInYear(year);
            if (alive.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + year);
            } else {
                System.out.println("Autores vivos en " + year + ":");
                alive.forEach(System.out::println);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida. Ingresa un número entero para el año.");
        }
    }

    private void listBooksByLanguage() {
        System.out.print("Ingrese el código de idioma (ej: en, fr): ");
        String lang = sc.nextLine().trim();
        if (lang.isBlank()) {
            System.out.println("Idioma vacío. Operación cancelada.");
            return;
        }
        List<Book> books = bookService.listBooksByLanguage(lang);
        if (books.isEmpty()) {
            System.out.println("No hay libros registrados en el idioma: " + lang);
        } else {
            System.out.println("Libros en idioma " + lang + ":");
            books.forEach(System.out::println);

            long count = bookService.countBooksByLanguage(lang);
            System.out.println("Total en base de datos para '" + lang + "': " + count);
        }
    }
}