package com.greenfield.smartcommunity.domain.user;

public class Resident extends User {
    public Resident(String userId, String name, String email, String passwordHash) {
        super(userId, name, email, passwordHash);
    }
}