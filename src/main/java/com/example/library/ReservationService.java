package com.example.library;

public class ReservationService {
    public ReservationResult reserveBook(LibraryMember member, Book book) {
        if (member == null || book == null) {
            return new ReservationResult(false, "Nieprawidłowe dane");
        }
        if (member.isBlocked()) {
            return new ReservationResult(false, "Konto członka jest zablokowane");
        }
        if (book.getCurrentBorrower() != null) {
            return new ReservationResult(false, "Książka jest obecnie wypożyczona, rezerwacja dodana do kolejki");
        }
        return new ReservationResult(true, "Rezerwacja przyjęta");
    }
}


