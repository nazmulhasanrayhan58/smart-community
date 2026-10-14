package com.greenfield.smartcommunity.integration.adapters;

import com.greenfield.smartcommunity.domain.request.ServiceRequest;
import com.greenfield.smartcommunity.integration.NotificationService;

public class ConsoleNotificationAdapter implements NotificationService {

    @Override
    public void sendStatusUpdate(ServiceRequest request, String recipientEmail) {
        System.out.printf("[NOTIFICATION -> %s] Request %s status changed to %s%n",
                recipientEmail, request.getRequestId(), request.getStatus());
    }

    @Override
    public void sendAssignmentNotice(ServiceRequest request, String teamId) {
        System.out.printf("[NOTIFICATION -> Team %s] Request %s assigned%n",
                teamId, request.getRequestId());
    }
}