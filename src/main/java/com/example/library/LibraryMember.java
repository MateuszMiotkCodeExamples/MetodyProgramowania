package com.example.library;

import java.time.LocalDate;

public class LibraryMember {
    private final String memberId;
    private final String name;
    private MemberCategory category;
    private boolean blocked;
    private boolean active;
    private LocalDate membershipDate;
    private String email;

    public LibraryMember(String memberId, String name) {
        this(memberId, name, MemberCategory.ADULT);
    }

    public LibraryMember(String memberId, String name, MemberCategory category) {
        this.memberId = memberId;
        this.name = name;
        this.category = category;
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public MemberCategory getCategory() { return category; }
    public void setCategory(MemberCategory category) { this.category = category; }
    public boolean isBlocked() { return blocked; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDate getMembershipDate() { return membershipDate; }
    public void setMembershipDate(LocalDate membershipDate) { this.membershipDate = membershipDate; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}


