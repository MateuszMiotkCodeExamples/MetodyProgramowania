package com.example.library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BorrowingServiceTest {

    @Test
    void shouldReturnSuccessWhenBorrowingAvailableBook() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        BorrowingService borrowingService = new BorrowingService();

        BorrowingResult result = borrowingService.borrowBook(member, book);

        assertTrue(result.isSuccessful());
    }

    @Test
    void shouldAssignBookToBorrowerWhenBorrowingSucceeds() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");
        BorrowingService borrowingService = new BorrowingService();

        borrowingService.borrowBook(member, book);

        assertEquals(member, book.getCurrentBorrower());
    }

    @Test
    void shouldReturnFailureWhenBookIsAlreadyBorrowed() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        LibraryMember firstMember = new LibraryMember("LM001", "Anna Kowalska");
        LibraryMember secondMember = new LibraryMember("LM002", "Jan Nowak");
        BorrowingService borrowingService = new BorrowingService();

        borrowingService.borrowBook(firstMember, book);

        BorrowingResult result = borrowingService.borrowBook(secondMember, book);

        assertFalse(result.isSuccessful());
    }

    @Test
    void shouldReturnAppropriateMessageWhenBookIsAlreadyBorrowed() {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        LibraryMember firstMember = new LibraryMember("LM001", "Anna Kowalska");
        LibraryMember secondMember = new LibraryMember("LM002", "Jan Nowak");
        BorrowingService borrowingService = new BorrowingService();

        borrowingService.borrowBook(firstMember, book);

        BorrowingResult result = borrowingService.borrowBook(secondMember, book);

        assertEquals("Książka jest już wypożyczona", result.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullMember() {
        BorrowingService service = new BorrowingService();
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");

        assertThrows(IllegalArgumentException.class, () -> service.borrowBook(null, book));
    }

    @Test
    void shouldThrowExceptionForNullBook() {
        BorrowingService service = new BorrowingService();
        LibraryMember member = new LibraryMember("LM001", "Anna Kowalska");

        assertThrows(IllegalArgumentException.class, () -> service.borrowBook(member, null));
    }
}


