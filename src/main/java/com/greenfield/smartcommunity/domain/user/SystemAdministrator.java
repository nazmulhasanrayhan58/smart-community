package com.greenfield.smartcommunity.domain.user;

public class SystemAdministrator extends User {
    private final int adminLevel;

    public SystemAdministrator(String userId, String name, String email, String passwordHash,
                               int adminLevel) {
        super(userId, name, email, passwordHash);
        this.adminLevel = adminLevel;
    }

    public int getAdminLevel() { return adminLevel; }
}