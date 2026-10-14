package com.greenfield.smartcommunity.service;

import com.greenfield.smartcommunity.domain.request.*;

public final class ServiceRequestFactory {

    private ServiceRequestFactory() {}

    public static ServiceRequest create(String category, String id,
                                        String description, GISLocation location) {
        return switch (category.toUpperCase()) {
            case "ROAD_DAMAGE"      -> new RoadDamageRequest(id, description, location);
            case "WASTE_COLLECTION" -> new WasteCollectionRequest(id, description, location);
            case "GRAFFITI"         -> new GraffitiRequest(id, description, location);
            case "STREET_LIGHTING"  -> new StreetLightingRequest(id, description, location);
            case "OTHER"            -> new OtherRequest(id, description, location);
            default -> throw new IllegalArgumentException("Unknown category: " + category);
        };
    }
}