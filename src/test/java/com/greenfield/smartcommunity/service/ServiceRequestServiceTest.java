package com.greenfield.smartcommunity.service;

import com.greenfield.smartcommunity.domain.enums.Priority;
import com.greenfield.smartcommunity.domain.enums.RequestStatus;
import com.greenfield.smartcommunity.domain.request.*;
import com.greenfield.smartcommunity.integration.adapters.ConsoleNotificationAdapter;
import com.greenfield.smartcommunity.integration.adapters.MockGISAdapter;
import com.greenfield.smartcommunity.persistence.impl.InMemoryRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceRequestServiceTest {

    private ServiceRequestService service;
    private GISLocation validLocation;

    @BeforeEach
    void setUp() {
        service = new ServiceRequestService(
                new InMemoryRequestRepository(),
                new ConsoleNotificationAdapter(),
                new MockGISAdapter()
        );
        validLocation = new GISLocation(-37.8136, 144.9631, "Melbourne CBD");
    }

    @Test
    void testCreateRoadDamageRequest_assignsHighPriority() {
        ServiceRequest request = service.createRequest(
                "ROAD_DAMAGE", "Pothole on Main St", validLocation);
        assertEquals(Priority.HIGH, request.getPriority());
        assertEquals(RequestStatus.SUBMITTED, request.getStatus());
    }

    @Test
    void testCreateWasteRequest_assignsMediumPriority() {
        ServiceRequest request = service.createRequest(
                "WASTE_COLLECTION", "Missed bin collection", validLocation);
        assertEquals(Priority.MEDIUM, request.getPriority());
    }

    @Test
    void testCreateRequest_rejectsInvalidGISLocation() {
        GISLocation invalid = new GISLocation(200, 200, "Invalid");
        assertThrows(IllegalArgumentException.class,
                () -> service.createRequest("OTHER", "desc", invalid));
    }

    @Test
    void testCreateRequest_rejectsBlankDescription() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createRequest("OTHER", "", validLocation));
    }

    @Test
    void testAttachmentLimit_enforced() {
        ServiceRequest request = service.createRequest("GRAFFITI", "Graffiti", validLocation);
        request.addAttachment(new Attachment("A1", "/p1.jpg", "image/jpeg"));
        request.addAttachment(new Attachment("A2", "/p2.jpg", "image/jpeg"));
        request.addAttachment(new Attachment("A3", "/p3.jpg", "image/jpeg"));
        assertThrows(IllegalStateException.class,
                () -> request.addAttachment(new Attachment("A4", "/p4.jpg", "image/jpeg")));
    }

    @Test
    void testStatusTransition_validFlow() {
        ServiceRequest request = service.createRequest("WASTE_COLLECTION", "Bin", validLocation);
        service.reviewRequest(request.getRequestId());
        assertEquals(RequestStatus.IN_REVIEW, request.getStatus());

        service.assignToTeam(request.getRequestId(), "TEAM-A");
        assertEquals(RequestStatus.ASSIGNED, request.getStatus());
    }

    @Test
    void testStatusTransition_invalidFlow() {
        ServiceRequest request = service.createRequest("GRAFFITI", "Tag", validLocation);
        assertThrows(IllegalStateException.class,
                () -> request.updateStatus(RequestStatus.RESOLVED));
    }

    @Test
    void testFindRequestsByStatus() {
        ServiceRequest r1 = service.createRequest("OTHER", "Test1", validLocation);
        service.createRequest("OTHER", "Test2", validLocation);
        service.reviewRequest(r1.getRequestId());

        List<ServiceRequest> inReview = service.getRequestsByStatus(RequestStatus.IN_REVIEW);
        assertEquals(1, inReview.size());
        assertEquals(r1.getRequestId(), inReview.get(0).getRequestId());
    }

    @Test
    void testFindById_throwsWhenMissing() {
        assertThrows(IllegalArgumentException.class,
                () -> service.reviewRequest("NON-EXISTENT"));
    }
}