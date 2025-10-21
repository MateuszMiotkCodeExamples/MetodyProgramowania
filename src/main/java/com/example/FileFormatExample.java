package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Przykładowa klasa demonstrujaca obsługę formatów CSV i JSON przy użyciu Maven.
 * Pokazuje jak Maven automatycznie zarządza zależnościami (Gson, OpenCSV).
 */
public class FileFormatExample {

    /**
     * Klasa reprezentująca książkę - używana do parsowania JSON i CSV.
     */
    public static class Book {
        private String title;
        private String author;
        private int year;
        private String isbn;

        public Book() {
        }

        public Book(String title, String author, int year, String isbn) {
            this.title = title;
            this.author = author;
            this.year = year;
            this.isbn = isbn;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public int getYear() {
            return year;
        }

        public String getIsbn() {
            return isbn;
        }

        @Override
        public String toString() {
            return String.format("Book{title='%s', author='%s', year=%d, isbn='%s'}", 
                title, author, year, isbn);
        }
    }

    /**
     * Wczytuje książki z pliku CSV używając biblioteki OpenCSV.
     * Plik CSV powinien mieć nagłówek i format: title,author,year,isbn
     *
     * @param resourcePath ścieżka do pliku CSV w resources
     * @return lista książek
     * @throws IOException jeśli wystąpi błąd odczytu
     * @throws CsvException jeśli wystąpi błąd parsowania CSV
     */
    public List<Book> loadBooksFromCSV(String resourcePath) throws IOException, CsvException {
        List<Book> books = new ArrayList<>();
        
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(reader)) {
            
            List<String[]> records = csvReader.readAll();
            
            // Pomijamy nagłówek (pierwszy wiersz)
            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                if (record.length >= 4) {
                    String title = record[0];
                    String author = record[1];
                    int year = Integer.parseInt(record[2]);
                    String isbn = record[3];
                    books.add(new Book(title, author, year, isbn));
                }
            }
        }
        
        return books;
    }

    /**
     * Wczytuje książki z pliku JSON używając biblioteki Gson.
     * Plik JSON powinien zawierać tablicę obiektów książek.
     *
     * @param resourcePath ścieżka do pliku JSON w resources
     * @return lista książek
     * @throws IOException jeśli wystąpi błąd odczytu
     */
    public List<Book> loadBooksFromJSON(String resourcePath) throws IOException {
        Gson gson = new Gson();
        
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            
            Book[] booksArray = gson.fromJson(reader, Book[].class);
            return List.of(booksArray);
        }
    }

    /**
     * Konwertuje listę książek do formatu JSON.
     *
     * @param books lista książek do konwersji
     * @return JSON string z książkami
     */
    public String booksToJSON(List<Book> books) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(books);
    }

    /**
     * Główna metoda demonstrująca użycie parsowania CSV i JSON.
     */
    public static void main(String[] args) {
        FileFormatExample example = new FileFormatExample();
        
        try {
            System.out.println("=== Wczytywanie książek z CSV ===");
            List<Book> booksFromCSV = example.loadBooksFromCSV("books.csv");
            booksFromCSV.forEach(System.out::println);
            
            System.out.println("\n=== Wczytywanie książek z JSON ===");
            List<Book> booksFromJSON = example.loadBooksFromJSON("books.json");
            booksFromJSON.forEach(System.out::println);
            
            System.out.println("\n=== Konwersja do JSON ===");
            String jsonOutput = example.booksToJSON(booksFromCSV);
            System.out.println(jsonOutput);
            
        } catch (Exception e) {
            System.err.println("Wystąpił błąd: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

