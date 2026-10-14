package com.greenfield.smartcommunity.domain.request;

import com.greenfield.smartcommunity.domain.enums.Priority;

public class OtherRequest extends ServiceRequest {
    public OtherRequest(String requestId, String description, GISLocation location) {
        super(requestId, description, location);
    }

    @Override
    public Priority getDefaultPriority() { return Priority.LOW; }
}