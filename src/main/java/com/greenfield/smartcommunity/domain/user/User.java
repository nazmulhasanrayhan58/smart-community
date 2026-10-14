package com.greenfield.smartcommunity.domain.user;

public abstract class User {
    private final String userId;
    private String name;
    private String email;
    private String passwordHash;
    private boolean isActive;
    private boolean isVerified;

    protected User(String userId, String name, String email, String passwordHash) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.isActive = true;
        this.isVerified = false;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isActive() { return isActive; }
    public boolean isVerified() { return isVerified; }

    public void setVerified(boolean verified) { this.isVerified = verified; }
    public void setActive(boolean active) { this.isActive = active; }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.passwordHash.equals(password) && isActive;
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, name=%s]", getClass().getSimpleName(), userId, name);
    }
}