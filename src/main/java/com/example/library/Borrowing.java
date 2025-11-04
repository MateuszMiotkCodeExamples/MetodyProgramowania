package com.example.library;

import java.time.LocalDate;

public class Borrowing {
    private final LibraryMember member;
    private final Book book;
    private final LocalDate borrowDate;
    private final LocalDate returnDate;

    public Borrowing(LibraryMember member, Book book) {
        this(member, book, LocalDate.now().minusDays(10), LocalDate.now().minusDays(1));
    }

    public Borrowing(LibraryMember member, Book book, LocalDate borrowDate, LocalDate returnDate) {
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public LibraryMember getMember() { return member; }
    public Book getBook() { return book; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
}


