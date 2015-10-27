package com.bitdubai.fermat_pip_addon.layer.platform_service.location_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.location_subsystem.LocationSubsystem;

/**
 * Implementation of the interface that is going to be used by upper layers
 * Created by firuzzz on 5/9/15.
 */
class LocationService implements LocationSubsystem {
    private Integer id;
    private String provider;
    private long time;
    private double latitude;
    private double longitude;
    private double altitude;
    private double accuracy;

    public LocationService(Integer id, String provider, long time, double latitude, double longitude, double altitude, double accuracy) {
        this.id = id;
        this.provider = provider;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.accuracy = accuracy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

}
