package com.example.library;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Book {
    private String id; // optional, used in recursive comparison tests
    private final String isbn;
    private String title;
    private String author;
    private Integer publicationYear;
    private Integer pageCount;
    private String language;
    private BookGenre genre;
    private BookFormat format;
    private final Set<String> tags = new HashSet<>();
    private LibraryMember currentBorrower;
    private Instant createdAt; // optional, used in recursive comparison tests

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public Integer getPageCount() { return pageCount; }
    public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public BookGenre getGenre() { return genre; }
    public void setGenre(BookGenre genre) { this.genre = genre; }
    public BookFormat getFormat() { return format; }
    public void setFormat(BookFormat format) { this.format = format; }
    public Set<String> getTags() { return tags; }
    public void addTag(String tag) { this.tags.add(tag); }
    public LibraryMember getCurrentBorrower() { return currentBorrower; }
    public void setCurrentBorrower(LibraryMember currentBorrower) { this.currentBorrower = currentBorrower; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}


