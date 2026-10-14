package com.greenfield.smartcommunity.domain.request;

import com.greenfield.smartcommunity.domain.enums.Priority;

public class GraffitiRequest extends ServiceRequest {
    public GraffitiRequest(String requestId, String description, GISLocation location) {
        super(requestId, description, location);
    }

    @Override
    public Priority getDefaultPriority() { return Priority.MEDIUM; }
}