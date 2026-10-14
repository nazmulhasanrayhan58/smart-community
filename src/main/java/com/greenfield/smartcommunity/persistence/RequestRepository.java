package com.greenfield.smartcommunity.persistence;

import com.greenfield.smartcommunity.domain.enums.RequestStatus;
import com.greenfield.smartcommunity.domain.request.ServiceRequest;

import java.util.List;
import java.util.Optional;

public interface RequestRepository {
    void save(ServiceRequest request);
    Optional<ServiceRequest> findById(String requestId);
    List<ServiceRequest> findAll();
    List<ServiceRequest> findByStatus(RequestStatus status);
}