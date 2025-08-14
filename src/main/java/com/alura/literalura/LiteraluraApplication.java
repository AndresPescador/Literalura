package com.alura.literalura;

import com.alura.literalura.service.AuthorService;
import com.alura.literalura.service.BookService;
import com.alura.literalura.util.ConsoleUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BookService bookService, AuthorService authorService) {
        return args -> {
            Scanner sc = new Scanner(System.in);
            ConsoleUtil console = new ConsoleUtil(sc, bookService, authorService);
            console.startMenuLoop();
        };
    }
}
