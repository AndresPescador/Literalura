package com.alura.literalura.util;

import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
import com.alura.literalura.service.AuthorService;
import com.alura.literalura.service.BookService;

import java.util.DoubleSummaryStatistics;
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
                case "1": searchBookByTitle(); break;
                case "2": listAllBooks(); break;
                case "3": listAllAuthors(); break;
                case "4": listAuthorsAliveInYear(); break;
                case "5": listBooksByLanguage(); break;
                case "6": listTop10MostDownloaded(); break;
                case "7": showDownloadStatistics(); break;
                case "8": listBooksByAuthorName(); break;
                case "9": listAuthorsBornAfterYear(); break;
                case "10": listAuthorsDiedBeforeYear(); break;
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
        System.out.println("1- Buscar libro por título");
        System.out.println("2- Listar libros registrados");
        System.out.println("3- Listar autores registrados");
        System.out.println("4- Listar autores vivos en un determinado año");
        System.out.println("5- Listar libros por idioma");
        System.out.println("6- Top 10 libros más descargados");
        System.out.println("7- Mostrar estadísticas de descargas");
        System.out.println("8- Buscar libros por nombre de autor");
        System.out.println("9- Listar autores nacidos después de un año");
        System.out.println("10- Listar autores que murieron antes de un año");
        System.out.println("0- Salir");
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
        books.forEach(System.out::println);
    }

    private void listAllAuthors() {
        List<Author> authors = authorService.listAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados aún.");
            return;
        }
        System.out.println("Autores registrados:");
        authors.forEach(System.out::println);
    }

    private void listAuthorsAliveInYear() {
        System.out.print("Ingrese el año para filtrar autores vivos en ese año (ej: 1900): ");
        try {
            int year = Integer.parseInt(sc.nextLine().trim());
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
        String lang = sc.nextLine().trim().toLowerCase();
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

    private void listTop10MostDownloaded() {
        List<Book> topBooks = bookService.getTop10MostDownloaded();
        if (topBooks.isEmpty()) {
            System.out.println("No hay datos de descargas.");
        } else {
            System.out.println("Top 10 libros más descargados:");
            topBooks.forEach(System.out::println);
        }
    }

    private void showDownloadStatistics() {
        DoubleSummaryStatistics stats = bookService.getDownloadStatistics();
        if (stats.getCount() == 0) {
            System.out.println("No hay datos de descargas.");
        } else {
            System.out.println("Estadísticas de descargas:");
            System.out.println("Total de libros con descargas: " + stats.getCount());
            System.out.println("Total de descargas: " + stats.getSum());
            System.out.println("Promedio de descargas: " + stats.getAverage());
            System.out.println("Mínimo: " + stats.getMin());
            System.out.println("Máximo: " + stats.getMax());
        }
    }

    private void listBooksByAuthorName() {
        System.out.print("Ingrese el nombre (o parte) del autor: ");
        String authorName = sc.nextLine().trim();
        if (authorName.isBlank()) {
            System.out.println("Nombre vacío. Operación cancelada.");
            return;
        }
        List<Book> books = bookService.findBooksByAuthorName(authorName);
        if (books.isEmpty()) {
            System.out.println("No se encontraron libros de autores que coincidan con: " + authorName);
        } else {
            System.out.println("Libros de autores que coinciden con '" + authorName + "':");
            books.forEach(System.out::println);
        }
    }

    private void listAuthorsBornAfterYear() {
        System.out.print("Ingrese el año para listar autores nacidos después: ");
        try {
            int year = Integer.parseInt(sc.nextLine().trim());
            List<Author> authors = authorService.findAuthorsBornAfter(year);
            if (authors.isEmpty()) {
                System.out.println("No se encontraron autores nacidos después de " + year);
            } else {
                System.out.println("Autores nacidos después de " + year + ":");
                authors.forEach(System.out::println);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida. Ingresa un número entero para el año.");
        }
    }

    private void listAuthorsDiedBeforeYear() {
        System.out.print("Ingrese el año para listar autores que murieron antes: ");
        try {
            int year = Integer.parseInt(sc.nextLine().trim());
            List<Author> authors = authorService.findAuthorsDiedBefore(year);
            if (authors.isEmpty()) {
                System.out.println("No se encontraron autores que murieran antes de " + year);
            } else {
                System.out.println("Autores que murieron antes de " + year + ":");
                authors.forEach(System.out::println);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida. Ingresa un número entero para el año.");
        }
    }
}