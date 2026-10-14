package com.greenfield.smartcommunity.service;

import com.greenfield.smartcommunity.domain.enums.RequestStatus;
import com.greenfield.smartcommunity.domain.request.*;
import com.greenfield.smartcommunity.integration.GISService;
import com.greenfield.smartcommunity.integration.NotificationService;
import com.greenfield.smartcommunity.persistence.RequestRepository;
import com.greenfield.smartcommunity.util.IdGenerator;

import java.util.List;

public class ServiceRequestService {

    private final RequestRepository repository;
    private final NotificationService notificationService;
    private final GISService gisService;

    public ServiceRequestService(RequestRepository repository,
                                 NotificationService notificationService,
                                 GISService gisService) {
        this.repository = repository;
        this.notificationService = notificationService;
        this.gisService = gisService;
    }

    public ServiceRequest createRequest(String category, String description, GISLocation location) {
        if (!gisService.validateLocation(location)) {
            throw new IllegalArgumentException("Invalid GIS location (BR-04)");
        }
        String id = IdGenerator.getInstance().nextRequestId();
        ServiceRequest request = ServiceRequestFactory.create(category, id, description, location);
        repository.save(request);
        return request;
    }

    public void reviewRequest(String requestId) {
        ServiceRequest request = fetchOrThrow(requestId);
        request.updateStatus(RequestStatus.IN_REVIEW);
        repository.save(request);
    }

    public void assignToTeam(String requestId, String teamId) {
        ServiceRequest request = fetchOrThrow(requestId);
        if (request.getStatus() != RequestStatus.IN_REVIEW) {
            throw new IllegalStateException("Request must be IN_REVIEW before assignment");
        }
        request.assignToTeam(teamId);
        repository.save(request);
        notificationService.sendAssignmentNotice(request, teamId);
    }

    public void updateStatus(String requestId, RequestStatus newStatus, String recipientEmail) {
        ServiceRequest request = fetchOrThrow(requestId);
        request.updateStatus(newStatus);
        repository.save(request);
        notificationService.sendStatusUpdate(request, recipientEmail);
    }

    public List<ServiceRequest> getAllRequests() {
        return repository.findAll();
    }

    public List<ServiceRequest> getRequestsByStatus(RequestStatus status) {
        return repository.findByStatus(status);
    }

    private ServiceRequest fetchOrThrow(String requestId) {
        return repository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));
    }
}