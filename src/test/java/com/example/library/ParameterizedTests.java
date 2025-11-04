package com.example.library;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ParameterizedTests {

    @ParameterizedTest
    @ValueSource(ints = {1, 7, 14, 21, 30})
    void shouldReturnPositiveFeeForAnyNumberOfDaysOverdue(int daysOverdue) {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        FeeCalculator calculator = new FeeCalculator();

        BigDecimal fee = calculator.calculateLateFee(book, daysOverdue);

        assertTrue(fee.compareTo(BigDecimal.ZERO) > 0);
    }

    @ParameterizedTest
    @CsvSource({
        "1, 0.50",
        "7, 3.50",
        "14, 7.00",
        "21, 10.50",
        "30, 15.00"
    })
    void shouldCalculateCorrectFeeAmountForDaysOverdue(int daysOverdue, String expectedFee) {
        Book book = new Book("9788324631766", "Czysty kod", "Robert C. Martin");
        FeeCalculator calculator = new FeeCalculator();

        BigDecimal fee = calculator.calculateLateFee(book, daysOverdue);

        assertEquals(new BigDecimal(expectedFee), fee);
    }

    @ParameterizedTest(name = "Kategoria {0} z {1} wypożyczeniami = {2} punktów")
    @CsvSource({
        "ADULT, 5, 5",
        "ADULT, 12, 12",
        "CHILD, 5, 10",
        "CHILD, 12, 24",
        "SENIOR, 5, 7",
        "SENIOR, 12, 18"
    })
    void shouldCalculateCorrectLoyaltyPoints(MemberCategory category, int borrowingsCount, int expectedPoints) {
        LibraryMember member = new LibraryMember("LM001", "Test Member", category);
        LoyaltyCalculator calculator = new LoyaltyCalculator();

        int actualPoints = calculator.calculatePoints(member, borrowingsCount);

        assertEquals(expectedPoints, actualPoints);
    }

    @ParameterizedTest
    @MethodSource("provideBookReservationScenarios")
    void shouldReturnCorrectSuccessStatusForReservation(Book book, LibraryMember member, boolean expectedSuccess) {
        ReservationService service = new ReservationService();

        ReservationResult result = service.reserveBook(member, book);

        assertEquals(expectedSuccess, result.isSuccessful());
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> provideBookReservationScenarios() {
        Book availableBook = new Book("9788324631766", "Czysty kod", "Robert C. Martin");

        Book borrowedBook = new Book("9788328302341", "Java. Efektywne programowanie", "Joshua Bloch");
        borrowedBook.setCurrentBorrower(new LibraryMember("LM999", "Inny Czytelnik"));

        LibraryMember standardMember = new LibraryMember("LM001", "Anna Kowalska");
        LibraryMember blockedMember = new LibraryMember("LM002", "Jan Nowak");
        blockedMember.setBlocked(true);

        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of(availableBook, standardMember, true),
            org.junit.jupiter.params.provider.Arguments.of(borrowedBook, standardMember, false),
            org.junit.jupiter.params.provider.Arguments.of(availableBook, blockedMember, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideReservationErrorMessages")
    void shouldReturnCorrectErrorMessageForFailedReservation(Book book, LibraryMember member, String expectedMessage) {
        ReservationService service = new ReservationService();

        ReservationResult result = service.reserveBook(member, book);

        assertEquals(expectedMessage, result.getMessage());
    }

    static Stream<org.junit.jupiter.params.provider.Arguments> provideReservationErrorMessages() {
        Book borrowedBook = new Book("9788328302341", "Java. Efektywne programowanie", "Joshua Bloch");
        borrowedBook.setCurrentBorrower(new LibraryMember("LM999", "Inny Czytelnik"));

        Book availableBook = new Book("9788324631766", "Czysty kod", "Robert C. Martin");

        LibraryMember standardMember = new LibraryMember("LM001", "Anna Kowalska");
        LibraryMember blockedMember = new LibraryMember("LM002", "Jan Nowak");
        blockedMember.setBlocked(true);

        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of(borrowedBook, standardMember,
                "Książka jest obecnie wypożyczona, rezerwacja dodana do kolejki"),
            org.junit.jupiter.params.provider.Arguments.of(availableBook, blockedMember,
                "Konto członka jest zablokowane")
        );
    }

    @ParameterizedTest
    @EnumSource(BookGenre.class)
    void shouldReturnNonNullLocationForEveryGenre(BookGenre genre) {
        Book book = new Book("9788324631766", "Testowa książka", "Autor");
        book.setGenre(genre);
        ShelfLocationService service = new ShelfLocationService();

        ShelfLocation location = service.calculateLocation(book);

        assertNotNull(location);
    }

    @ParameterizedTest
    @CsvSource({
        "FICTION, FIC",
        "NON_FICTION, NF",
        "SCIENCE, SCI",
        "BIOGRAPHY, BIO",
        "CHILDREN, CHI"
    })
    void shouldAssignCorrectSectionCodeForGenre(BookGenre genre, String expectedCode) {
        Book book = new Book("9788324631766", "Testowa książka", "Autor");
        book.setGenre(genre);
        ShelfLocationService service = new ShelfLocationService();

        ShelfLocation location = service.calculateLocation(book);

        assertTrue(location.getSection().startsWith(expectedCode));
    }
}


