package com.greenfield.smartcommunity.integration.adapters;

import com.greenfield.smartcommunity.domain.request.GISLocation;
import com.greenfield.smartcommunity.integration.GISService;

public class MockGISAdapter implements GISService {

    @Override
    public boolean validateLocation(GISLocation location) {
        return location != null
                && location.getLatitude() >= -90 && location.getLatitude() <= 90
                && location.getLongitude() >= -180 && location.getLongitude() <= 180;
    }
}