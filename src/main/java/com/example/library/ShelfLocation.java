package com.example.library;

public class ShelfLocation {
    private final String section;
    private final int shelfNumber;

    public ShelfLocation(String section, int shelfNumber) {
        this.section = section;
        this.shelfNumber = shelfNumber;
    }

    public String getSection() { return section; }
    public int getShelfNumber() { return shelfNumber; }
}


