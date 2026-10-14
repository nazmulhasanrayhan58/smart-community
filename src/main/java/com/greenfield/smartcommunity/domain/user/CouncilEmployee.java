package com.greenfield.smartcommunity.domain.user;

public class CouncilEmployee extends User {
    private final String staffId;
    private final String department;

    public CouncilEmployee(String userId, String name, String email, String passwordHash,
                           String staffId, String department) {
        super(userId, name, email, passwordHash);
        this.staffId = staffId;
        this.department = department;
    }

    public String getStaffId() { return staffId; }
    public String getDepartment() { return department; }
}