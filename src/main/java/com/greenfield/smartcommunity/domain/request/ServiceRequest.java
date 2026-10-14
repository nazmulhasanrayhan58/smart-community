package com.greenfield.smartcommunity.domain.request;

import com.greenfield.smartcommunity.domain.enums.Priority;
import com.greenfield.smartcommunity.domain.enums.RequestStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ServiceRequest {
    private final String requestId;
    private final String description;
    private final GISLocation location;
    private final LocalDateTime dateSubmitted;
    private RequestStatus status;
    private Priority priority;
    private String assignedTeamId;
    private final List<Attachment> attachments;

    protected ServiceRequest(String requestId, String description, GISLocation location) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required (FR-05)");
        }
        if (location == null) {
            throw new IllegalArgumentException("GIS location is required (BR-04)");
        }
        this.requestId = requestId;
        this.description = description;
        this.location = location;
        this.dateSubmitted = LocalDateTime.now();
        this.status = RequestStatus.SUBMITTED;
        this.priority = getDefaultPriority();
        this.attachments = new ArrayList<>();
    }

    public abstract Priority getDefaultPriority();

    public void addAttachment(Attachment attachment) {
        if (attachments.size() >= 3) {
            throw new IllegalStateException("Maximum 3 attachments allowed (FR-06)");
        }
        attachments.add(attachment);
    }

    public void updateStatus(RequestStatus newStatus) {
        if (!isValidTransition(this.status, newStatus)) {
            throw new IllegalStateException(
                    "Invalid status transition: " + this.status + " -> " + newStatus);
        }
        this.status = newStatus;
    }

    public void assignToTeam(String teamId) {
        if (teamId == null || teamId.isBlank()) {
            throw new IllegalArgumentException("Team ID required");
        }
        this.assignedTeamId = teamId;
        this.status = RequestStatus.ASSIGNED;
    }

    private boolean isValidTransition(RequestStatus from, RequestStatus to) {
        return switch (from) {
            case SUBMITTED -> to == RequestStatus.IN_REVIEW;
            case IN_REVIEW -> to == RequestStatus.ASSIGNED;
            case ASSIGNED -> to == RequestStatus.IN_PROGRESS;
            case IN_PROGRESS -> to == RequestStatus.RESOLVED;
            case RESOLVED -> to == RequestStatus.CLOSED;
            case CLOSED -> false;
        };
    }

    public String getRequestId() { return requestId; }
    public String getDescription() { return description; }
    public GISLocation getLocation() { return location; }
    public LocalDateTime getDateSubmitted() { return dateSubmitted; }
    public RequestStatus getStatus() { return status; }
    public Priority getPriority() { return priority; }
    public String getAssignedTeamId() { return assignedTeamId; }
    public List<Attachment> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, status=%s, priority=%s]",
                getClass().getSimpleName(), requestId, status, priority);
    }
}