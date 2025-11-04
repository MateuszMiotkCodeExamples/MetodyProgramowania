package com.example.library;

import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class AssertJTests {

    @Test
    void shouldHaveCorrectMemberId() {
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        assertThat(member.getMemberId()).isEqualTo("LM0012");
    }

    @Test
    void shouldHaveNonEmptyName() {
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        assertThat(member.getName()).isNotEmpty();
    }

    @Test
    void shouldHaveNameContainingSurname() {
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        assertThat(member.getName()).contains("Kowalska");
    }

    @Test
    void shouldBeActiveByDefault() {
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        member.setActive(true);
        assertThat(member.isActive()).isTrue();
    }

    @Test
    void shouldHaveMembershipDateInPast() {
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        member.setMembershipDate(LocalDate.of(2020, 3, 15));
        assertThat(member.getMembershipDate()).isBefore(LocalDate.now());
    }

    @Test
    void shouldHaveThreeBorrowingsInHistory() {
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        List<Borrowing> history = Arrays.asList(
            new Borrowing(member, new Book("ISBN1", "Książka 1", "Autor 1")),
            new Borrowing(member, new Book("ISBN2", "Książka 2", "Autor 2")),
            new Borrowing(member, new Book("ISBN3", "Książka 3", "Autor 3"))
        );

        assertThat(history).hasSize(3);
    }

    @Test
    void shouldHaveNonEmptyBorrowingHistory() {
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        List<Borrowing> history = Arrays.asList(
            new Borrowing(member, new Book("ISBN1", "Książka 1", "Autor 1"))
        );

        assertThat(history).isNotEmpty();
    }

    @Test
    void shouldContainExpectedAuthorsInHistory() {
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        List<Borrowing> history = Arrays.asList(
            new Borrowing(member, new Book("ISBN1", "Książka 1", "Robert C. Martin")),
            new Borrowing(member, new Book("ISBN2", "Książka 2", "Joshua Bloch")),
            new Borrowing(member, new Book("ISBN3", "Książka 3", "Andrew Hunt"))
        );

        assertThat(history)
            .extracting(b -> b.getBook().getAuthor())
            .containsExactlyInAnyOrder("Robert C. Martin", "Joshua Bloch", "Andrew Hunt");
    }

    @Test
    void shouldHaveOneCurrentlyBorrowedBook() {
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        List<Borrowing> history = Arrays.asList(
            new Borrowing(member, new Book("ISBN1", "Książka 1", "Autor 1"),
                LocalDate.of(2024, 1, 15), LocalDate.of(2024, 2, 5)),
            new Borrowing(member, new Book("ISBN2", "Książka 2", "Autor 2"),
                LocalDate.of(2024, 3, 5), null)
        );

        assertThat(history)
            .filteredOn(b -> b.getReturnDate() == null)
            .hasSize(1);
    }

    @Test
    void shouldHaveAllBorrowingsInPast() {
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        List<Borrowing> history = Arrays.asList(
            new Borrowing(member, new Book("ISBN1", "Książka 1", "Autor 1"),
                LocalDate.of(2024, 1, 15), null),
            new Borrowing(member, new Book("ISBN2", "Książka 2", "Autor 2"),
                LocalDate.of(2024, 2, 10), null)
        );

        assertThat(history)
            .extracting(Borrowing::getBorrowDate)
            .allMatch(date -> date.isBefore(LocalDate.now()));
    }

    @Test
    void shouldBeEqualToExpectedBook() {
        Book expectedBook = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        expectedBook.setPublicationYear(2008);
        expectedBook.setPageCount(462);

        Book actualBook = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        actualBook.setPublicationYear(2008);
        actualBook.setPageCount(462);

        assertThat(actualBook)
            .usingRecursiveComparison()
            .ignoringFields("id", "createdAt")
            .isEqualTo(expectedBook);
    }

    @Test
    void shouldBeClassicBook() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        book.setPublicationYear(2008);

        Condition<Book> isClassic = new Condition<>(
            b -> b.getPublicationYear() < 2010,
            "klasyczna pozycja (sprzed 2010 roku)"
        );

        assertThat(book).has(isClassic);
    }

    @Test
    void shouldBeSubstantialBook() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        book.setPageCount(462);

        Condition<Book> isSubstantial = new Condition<>(
            b -> b.getPageCount() > 300,
            "obszerna książka (ponad 300 stron)"
        );

        assertThat(book).has(isSubstantial);
    }

    @Test
    void shouldValidateAllBookPropertiesAtOnce() {
        Book book = new Book("9788324631766", "Czysty kod: Podręcznik", "Robert C. Martin");
        book.setPublicationYear(2008);
        book.setPageCount(462);
        book.setLanguage("Polski");

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(book.getTitle())
            .as("sprawdzenie tytułu")
            .contains("kod");

        softly.assertThat(book.getAuthor())
            .as("sprawdzenie autora")
            .contains("Martin");

        softly.assertThat(book.getPublicationYear())
            .as("sprawdzenie roku publikacji")
            .isGreaterThan(2000);

        softly.assertThat(book.getPageCount())
            .as("sprawdzenie liczby stron")
            .isPositive();

        softly.assertThat(book.getIsbn())
            .as("sprawdzenie formatu ISBN")
            .matches("\\d{13}");

        softly.assertAll();
    }
}


