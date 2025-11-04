package com.example.library;

public class LoyaltyCalculator {
    public int calculatePoints(LibraryMember member, int borrowingsCount) {
        MemberCategory category = member.getCategory();
        switch (category) {
            case ADULT:
                return borrowingsCount;
            case CHILD:
                return borrowingsCount * 2;
            case SENIOR:
                return (int) Math.floor(borrowingsCount * 1.5);
            default:
                return borrowingsCount;
        }
    }
}


