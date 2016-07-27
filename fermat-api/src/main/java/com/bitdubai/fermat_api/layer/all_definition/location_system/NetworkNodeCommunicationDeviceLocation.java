package com.bitdubai.fermat_api.layer.all_definition.location_system;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;

import org.apache.commons.lang.NotImplementedException;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.wrappers.DeviceLocation</code>
 * contains the coordinates of a point and the time when it was calculated.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class NetworkNodeCommunicationDeviceLocation implements Location {

    private final Double latitude;
    private final Double longitude;
    private final Double altitude;
    private final long accuracy;
    private final Double altitudeAccuracy;
    private final long time;
    private final LocationSource source;


    /*
     * Constructor without params
     */
    public NetworkNodeCommunicationDeviceLocation() {
        this.altitude = null;
        this.accuracy = 0;
        this.altitudeAccuracy = null;
        this.source = null;
        this.time = 0;
        latitude = null;
        longitude = null;
    }

    /**
     * Constructor with params:
     *
     * @param latitude         geographic coordinates specified in decimal degrees.
     * @param longitude        geographic coordinates specified in decimal degrees.
     * @param altitude         denotes the height of the position, specified in meters above the [WGS84] ellipsoid.
     * @param accuracy         denotes the accuracy level of the latitude and longitude. It is specified in meters.
     * @param altitudeAccuracy is specified in meters.
     * @param time             time when the position was calculated.
     */
    public NetworkNodeCommunicationDeviceLocation(final double latitude,
                                                  final double longitude,
                                                  final Double altitude,
                                                  final long accuracy,
                                                  final Double altitudeAccuracy,
                                                  final long time,
                                                  final LocationSource source) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.accuracy = accuracy;
        this.altitudeAccuracy = altitudeAccuracy;
        this.time = time;
        this.source = source;
    }

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public Double getAltitude() {
        return altitude;
    }

    @Override
    public long getAccuracy() {
        return accuracy;
    }

    @Override
    public Double getAltitudeAccuracy() {
        return altitudeAccuracy;
    }

    @Override
    public Long getTime() {
        return time;
    }

    @Override
    public LocationSource getSource() {
        return source;
    }

    @Override
    public void setLatitude(Double latitude) {

        throw new NotImplementedException("Method not implemented");
    }

    @Override
    public void setLongitude(Double longitude) {

        throw new NotImplementedException("Method not implemented");
    }

    @Override
    public void setAccuracy(long accuracy) {

        throw new NotImplementedException("Method not implemented");
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("DeviceLocation{")
                .append("latitude=").append(latitude)
                .append(", longitude=").append(longitude)
                .append(", altitude=").append(altitude)
                .append(", accuracy=").append(accuracy)
                .append(", altitudeAccuracy=").append(altitudeAccuracy)
                .append(", time=").append(time)
                .append(", source=").append(source)
                .append('}').toString();
    }

    /**
     * Create a instance whit the parameters
     *
     * @param latitude
     * @param longitude
     * @return NetworkNodeCommunicationDeviceLocation
     */
    public static NetworkNodeCommunicationDeviceLocation getInstance(final Double latitude, final Double longitude) {
        return new NetworkNodeCommunicationDeviceLocation(latitude,
                longitude,
                null,
                0,
                null,
                0,
                LocationSource.UNKNOWN
        );
    }
}
