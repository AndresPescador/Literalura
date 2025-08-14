package com.alura.literalura.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "language")
    private String language;

    @Column(name = "download_count")
    private Integer downloadCount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "author_id")
    private Author author;

    public Book() {}

    public Book(String title, String language, Integer downloadCount, Author author) {
        this.title = title;
        this.language = language;
        this.downloadCount = downloadCount;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", downloadCount=" + downloadCount +
                ", author=" + (author != null ? author.getName() : "null") +
                '}';
    }
}