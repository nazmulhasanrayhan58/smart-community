package com.greenfield.smartcommunity.domain.user;

public class MaintenanceTeamMember extends User {
    private final String teamId;
    private final String specialization;

    public MaintenanceTeamMember(String userId, String name, String email, String passwordHash,
                                 String teamId, String specialization) {
        super(userId, name, email, passwordHash);
        this.teamId = teamId;
        this.specialization = specialization;
    }

    public String getTeamId() { return teamId; }
    public String getSpecialization() { return specialization; }
}