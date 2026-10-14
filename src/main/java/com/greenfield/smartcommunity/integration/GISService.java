package com.greenfield.smartcommunity.integration;

import com.greenfield.smartcommunity.domain.request.GISLocation;

public interface GISService {
    boolean validateLocation(GISLocation location);
}