package com.example.library;

public class ShelfLocationService {
    public ShelfLocation calculateLocation(Book book) {
        String codePrefix;
        if (book.getGenre() == null) {
            codePrefix = "GEN";
        } else {
            switch (book.getGenre()) {
                case FICTION: codePrefix = "FIC"; break;
                case NON_FICTION: codePrefix = "NF"; break;
                case SCIENCE: codePrefix = "SCI"; break;
                case BIOGRAPHY: codePrefix = "BIO"; break;
                case CHILDREN: codePrefix = "CHI"; break;
                default: codePrefix = "GEN";
            }
        }
        return new ShelfLocation(codePrefix + "-A", 1);
    }
}


