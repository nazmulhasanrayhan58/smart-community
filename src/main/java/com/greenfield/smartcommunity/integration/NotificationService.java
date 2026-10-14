package com.greenfield.smartcommunity.integration;

import com.greenfield.smartcommunity.domain.request.ServiceRequest;

public interface NotificationService {
    void sendStatusUpdate(ServiceRequest request, String recipientEmail);
    void sendAssignmentNotice(ServiceRequest request, String teamId);
}