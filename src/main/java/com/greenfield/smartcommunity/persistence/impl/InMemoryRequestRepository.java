package com.greenfield.smartcommunity.persistence.impl;

import com.greenfield.smartcommunity.domain.enums.RequestStatus;
import com.greenfield.smartcommunity.domain.request.ServiceRequest;
import com.greenfield.smartcommunity.persistence.RequestRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryRequestRepository implements RequestRepository {

    private final Map<String, ServiceRequest> store = new LinkedHashMap<>();

    @Override
    public void save(ServiceRequest request) {
        store.put(request.getRequestId(), request);
    }

    @Override
    public Optional<ServiceRequest> findById(String requestId) {
        return Optional.ofNullable(store.get(requestId));
    }

    @Override
    public List<ServiceRequest> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<ServiceRequest> findByStatus(RequestStatus status) {
        return store.values().stream()
                .filter(r -> r.getStatus() == status)
                .collect(Collectors.toList());
    }
}