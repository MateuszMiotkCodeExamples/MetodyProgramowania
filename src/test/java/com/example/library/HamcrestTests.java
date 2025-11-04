package com.example.library;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class HamcrestTests {

    @Test
    void shouldHaveCorrectIsbn() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        assertThat(book.getIsbn(), is(equalTo("9788324631766")));
    }

    @Test
    void shouldHaveNonEmptyTitle() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        assertThat(book.getTitle(), is(not(emptyString())));
    }

    @Test
    void shouldHaveNonNullAuthor() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        assertThat(book.getAuthor(), is(notNullValue()));
    }

    @Test
    void shouldHavePublicationYearInPast() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        book.setPublicationYear(2008);
        assertThat(book.getPublicationYear(), lessThan(LocalDate.now().getYear()));
    }

    @Test
    void shouldHaveSubstantialPageCount() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        book.setPageCount(462);
        assertThat(book.getPageCount(), greaterThan(400));
    }

    @Test
    void shouldContainThreeBooks() {
        List<Book> programmingBooks = new ArrayList<>();
        programmingBooks.add(new Book("9788324631766", "Czysty kod", "Robert C. Martin"));
        programmingBooks.add(new Book("9788328302341", "Java. Efektywne programowanie", "Joshua Bloch"));
        programmingBooks.add(new Book("9788324677610", "Pragmatyczny programista", "Andrew Hunt"));

        assertThat(programmingBooks, hasSize(3));
    }

    @Test
    void shouldContainSpecificBook() {
        List<Book> programmingBooks = new ArrayList<>();
        programmingBooks.add(new Book("9788324631766", "Czysty kod", "Robert C. Martin"));
        programmingBooks.add(new Book("9788328302341", "Java. Efektywne programowanie", "Joshua Bloch"));

        assertThat(programmingBooks, hasItem(hasProperty("title", equalTo("Czysty kod"))));
    }

    @Test
    void shouldHaveIsbnForEveryBook() {
        List<Book> programmingBooks = new ArrayList<>();
        programmingBooks.add(new Book("9788324631766", "Czysty kod", "Robert C. Martin"));
        programmingBooks.add(new Book("9788328302341", "Java. Efektywne programowanie", "Joshua Bloch"));

        assertThat(programmingBooks, everyItem(hasProperty("isbn", notNullValue())));
    }

    @Test
    void shouldContainAllExpectedAuthors() {
        List<Book> programmingBooks = new ArrayList<>();
        programmingBooks.add(new Book("9788324631766", "Czysty kod", "Robert C. Martin"));
        programmingBooks.add(new Book("9788328302341", "Java. Efektywne programowanie", "Joshua Bloch"));
        programmingBooks.add(new Book("9788324677610", "Pragmatyczny programista", "Andrew Hunt"));

        List<String> authors = programmingBooks.stream().map(Book::getAuthor).collect(Collectors.toList());
        assertThat(authors, containsInAnyOrder("Robert C. Martin", "Joshua Bloch", "Andrew Hunt"));
    }

    @Test
    void shouldBeClassicProgrammingBook() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        book.setPublicationYear(2008);
        book.setPageCount(462);
        book.addTag("programming");
        book.addTag("best-practices");

        assertThat(book, allOf(
            hasProperty("publicationYear", lessThan(2010)),
            hasProperty("pageCount", greaterThan(400)),
            hasProperty("tags", hasItem("best-practices"))
        ));
    }

    @Test
    void shouldBeModernBookOrEbook() {
        Book book = new Book("9788324677610", "Nowoczesne podejście", "Jan Kowalski");
        book.setPublicationYear(2023);
        book.setFormat(BookFormat.EBOOK);

        assertThat(book, anyOf(
            hasProperty("publicationYear", greaterThan(2020)),
            hasProperty("format", equalTo(BookFormat.EBOOK))
        ));
    }

    @Test
    void shouldHaveIsbnStartingWithPrefix() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        assertThat(book.getIsbn(), startsWith("978"));
    }

    @Test
    void shouldHaveIsbnEndingWithSpecificDigits() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        assertThat(book.getIsbn(), endsWith("31766"));
    }

    @Test
    void shouldContainKeywordInTitle() {
        Book book = new Book("9788324631766", "Czysty kod: Podręcznik", "Robert C. Martin");
        assertThat(book.getTitle(), containsString("Czysty kod"));
    }

    @Test
    void shouldContainWordIgnoringCase() {
        Book book = new Book("9788324631766", "Czysty kod: Podręcznik", "Robert C. Martin");
        assertThat(book.getTitle(), containsStringIgnoringCase("podręcznik"));
    }
}


