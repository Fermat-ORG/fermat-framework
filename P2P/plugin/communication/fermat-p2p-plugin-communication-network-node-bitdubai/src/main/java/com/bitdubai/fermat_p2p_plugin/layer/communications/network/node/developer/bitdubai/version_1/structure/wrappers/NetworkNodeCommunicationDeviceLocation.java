package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.wrappers;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.wrappers.DeviceLocation</code>
 * contains the coordinates of a point and the time when it was calculated.
 *
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public final class NetworkNodeCommunicationDeviceLocation implements Location {

    private final double         latitude        ;
    private final double         longitude       ;
    private final Double         altitude        ;
    private final Double         accuracy        ;
    private final Double         altitudeAccuracy;
    private final long           time            ;
    private final LocationSource source          ;


    /**
     * Constructor
     */
    public NetworkNodeCommunicationDeviceLocation(){
        super();
        this.latitude         = 0;
        this.longitude        = 0;
        this.altitude         = new Double(0);
        this.accuracy         = new Double(0);
        this.altitudeAccuracy = new Double(0);
        this.time             = 0;
        this.source           = LocationSource.UNKNOWN;
    }

    /**
     * Constructor with params:
     *
     * @param latitude          geographic coordinates specified in decimal degrees.
     * @param longitude         geographic coordinates specified in decimal degrees.
     * @param altitude          denotes the height of the position, specified in meters above the [WGS84] ellipsoid.
     * @param accuracy          denotes the accuracy level of the latitude and longitude. It is specified in meters.
     * @param altitudeAccuracy  is specified in meters.
     * @param time              time when the position was calculated.
     */
    public NetworkNodeCommunicationDeviceLocation(final double         latitude        ,
                                                  final double         longitude       ,
                                                  final Double         altitude        ,
                                                  final Double         accuracy        ,
                                                  final Double         altitudeAccuracy,
                                                  final long           time            ,
                                                  final LocationSource source          ) {

        this.latitude         = latitude        ;
        this.longitude        = longitude       ;
        this.altitude         = altitude        ;
        this.accuracy         = accuracy        ;
        this.altitudeAccuracy = altitudeAccuracy;
        this.time             = time            ;
        this.source           = source          ;
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
    public Double getAccuracy() {
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
    public String toString() {
        return "DeviceLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", accuracy=" + accuracy +
                ", altitudeAccuracy=" + altitudeAccuracy +
                ", time=" + time +
                ", source=" + source +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetworkNodeCommunicationDeviceLocation)) return false;

        NetworkNodeCommunicationDeviceLocation that = (NetworkNodeCommunicationDeviceLocation) o;

        if (Double.compare(that.getLatitude(), getLatitude()) != 0) return false;
        if (Double.compare(that.getLongitude(), getLongitude()) != 0) return false;
        if (getTime() != that.getTime()) return false;
        if (getAltitude() != null ? !getAltitude().equals(that.getAltitude()) : that.getAltitude() != null)
            return false;
        if (getAccuracy() != null ? !getAccuracy().equals(that.getAccuracy()) : that.getAccuracy() != null)
            return false;
        if (getAltitudeAccuracy() != null ? !getAltitudeAccuracy().equals(that.getAltitudeAccuracy()) : that.getAltitudeAccuracy() != null)
            return false;
        return getSource() == that.getSource();

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getLatitude());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLongitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getAltitude() != null ? getAltitude().hashCode() : 0);
        result = 31 * result + (getAccuracy() != null ? getAccuracy().hashCode() : 0);
        result = 31 * result + (getAltitudeAccuracy() != null ? getAltitudeAccuracy().hashCode() : 0);
        result = 31 * result + (int) (getTime() ^ (getTime() >>> 32));
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        return result;
    }
}
