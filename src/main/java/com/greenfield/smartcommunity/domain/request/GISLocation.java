package com.greenfield.smartcommunity.domain.request;

public class GISLocation {
    private final double latitude;
    private final double longitude;
    private final String address;

    public GISLocation(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return String.format("(%.4f, %.4f) %s", latitude, longitude, address);
    }
}