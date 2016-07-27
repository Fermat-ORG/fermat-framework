package com.bitdubai.fermat_api.layer.all_definition.location_system;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;

/**
 * Created by Natalia on 30/04/2015.
 * Update by Roberto Requena -(rart3001@gmail.com) on 22/09/2015
 */
public class DeviceLocation implements Location {

    /**
     * Location interface member variables.
     */

    private Double latitude;
    private Double longitude;
    private long accuracy;
    private Long time;
    private Double altitude;
    private LocationSource provider;

    // Public constructor declarations.
    public DeviceLocation() {
        this.latitude = null;
        this.longitude = null;
        this.time = null;
        this.altitude = null;
        this.provider = null;
    }

    /**
     * <p>DeviceLocation implementation constructor
     *
     * @param latitude  Double actual device latitude
     * @param longitude Double actual device longitude
     * @param time      Long actual device location time
     * @param altitude  Double actual device  altitude
     * @param provider  enum LocationSource actual location provider network
     */
    public DeviceLocation(Double latitude, Double longitude, Long time, Double altitude, LocationSource provider) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.altitude = altitude;
        this.provider = provider;
    }


    /**
     * Location interfaces implementation.
     */

    /**
     * <p>This method gets de actual device altitude
     *
     * @return double altitude
     */
    @Override
    public Double getAltitude() {
        return altitude;
    }

    /**
     * Set the Altitude
     *
     * @param altitude
     */
    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    /**
     * <p>This method gets de actual device latitude
     *
     * @return double latitude
     */
    @Override
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Set the Latitude
     *
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * <p>This method gets de actual device longitude
     *
     * @return double longitude
     */
    @Override
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Set the Longitude
     *
     * @param longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public void setAccuracy(long accuracy) {
        this.accuracy = accuracy;
    }

    /**
     * <p>This method gets de actual location network provider
     *
     * @return LocationSource enum
     */
    @Override
    public LocationSource getSource() {
        return provider;
    }

    @Override
    public long getAccuracy() {
        return accuracy;
    }

    @Override
    public Double getAltitudeAccuracy() {
        return 0.0;
    }

    /**
     * Set the Provider
     *
     * @param provider
     */
    public void setProvider(LocationSource provider) {
        this.provider = provider;
    }

    /**
     * <p>This method gets de actual device location time
     *
     * @return long time
     */
    @Override
    public Long getTime() {
        return time;
    }

    /**
     * Set the Time
     *
     * @param time
     */
    public void setTime(Long time) {
        this.time = time;
    }
}
