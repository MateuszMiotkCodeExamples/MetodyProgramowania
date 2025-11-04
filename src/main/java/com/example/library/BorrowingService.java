package com.example.library;

public class BorrowingService {
    public BorrowingResult borrowBook(LibraryMember member, Book book) {
        if (member == null) {
            throw new IllegalArgumentException("Członek biblioteki nie może być null");
        }
        if (book == null) {
            throw new IllegalArgumentException("Książka nie może być null");
        }
        if (book.getCurrentBorrower() != null) {
            return new BorrowingResult(false, "Książka jest już wypożyczona");
        }
        if (member.isBlocked()) {
            return new BorrowingResult(false, "Konto członka jest zablokowane");
        }

        book.setCurrentBorrower(member);
        return new BorrowingResult(true, "Książka została wypożyczona");
    }
}


