package com.example;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy FileFormatExample.
 * Demonstracja użycia JUnit Jupiter z zakresem 'test' w Maven.
 */
class FileFormatExampleTest {

    private FileFormatExample example;

    @BeforeEach
    void setUp() {
        example = new FileFormatExample();
    }

    @Test
    void testLoadBooksFromCSV() throws IOException, CsvException {
        // Given
        String resourcePath = "books.csv";

        // When
        List<FileFormatExample.Book> books = example.loadBooksFromCSV(resourcePath);

        // Then
        assertNotNull(books, "Lista książek nie powinna być null");
        assertEquals(5, books.size(), "Powinno być 5 książek w pliku CSV");
        
        FileFormatExample.Book firstBook = books.get(0);
        assertEquals("Władca Pierścieni", firstBook.getTitle());
        assertEquals("J.R.R. Tolkien", firstBook.getAuthor());
        assertEquals(1954, firstBook.getYear());
        assertEquals("978-83-8110-120-2", firstBook.getIsbn());
    }

    @Test
    void testLoadBooksFromJSON() throws IOException {
        // Given
        String resourcePath = "books.json";

        // When
        List<FileFormatExample.Book> books = example.loadBooksFromJSON(resourcePath);

        // Then
        assertNotNull(books, "Lista książek nie powinna być null");
        assertEquals(5, books.size(), "Powinno być 5 książek w pliku JSON");
        
        FileFormatExample.Book firstBook = books.get(0);
        assertEquals("Władca Pierścieni", firstBook.getTitle());
        assertEquals("J.R.R. Tolkien", firstBook.getAuthor());
        assertEquals(1954, firstBook.getYear());
    }

    @Test
    void testBooksToJSON() {
        // Given
        List<FileFormatExample.Book> books = List.of(
            new FileFormatExample.Book("Testowa Książka", "Jan Kowalski", 2023, "123-456-789")
        );

        // When
        String json = example.booksToJSON(books);

        // Then
        assertNotNull(json, "JSON nie powinien być null");
        assertTrue(json.contains("Testowa Książka"), "JSON powinien zawierać tytuł książki");
        assertTrue(json.contains("Jan Kowalski"), "JSON powinien zawierać autora");
        assertTrue(json.contains("2023"), "JSON powinien zawierać rok wydania");
    }

    @Test
    void testCSVAndJSONContainSameData() throws IOException, CsvException {
        // When
        List<FileFormatExample.Book> booksFromCSV = example.loadBooksFromCSV("books.csv");
        List<FileFormatExample.Book> booksFromJSON = example.loadBooksFromJSON("books.json");

        // Then
        assertEquals(booksFromCSV.size(), booksFromJSON.size(), 
            "CSV i JSON powinny zawierać tę samą liczbę książek");
        
        for (int i = 0; i < booksFromCSV.size(); i++) {
            FileFormatExample.Book csvBook = booksFromCSV.get(i);
            FileFormatExample.Book jsonBook = booksFromJSON.get(i);
            
            assertEquals(csvBook.getTitle(), jsonBook.getTitle(), 
                "Tytuły książek powinny być identyczne");
            assertEquals(csvBook.getAuthor(), jsonBook.getAuthor(), 
                "Autorzy powinni być identyczni");
            assertEquals(csvBook.getYear(), jsonBook.getYear(), 
                "Lata wydania powinny być identyczne");
        }
    }
}

