package com.bitdubai.fermat_osa_addon.layer.android.location_system.developer.bitdubai.version_1.structure;

import android.content.Context;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationProvider;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;

/**
 * Created by Natalia on 21/05/2015.
 */

/**
 * This addon handles a layer of Device Location representation.
 * Encapsulates all the necessary functions to retrieve the geolocation of the device.
 *
 * * * *
 */

public class DeviceLocationManager implements LocationManager,LocationListener{

    /**
     * LocationManager Interface member variables.
     */
    private Context context;
    private double lat;
    private double lng;

    public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	/**
     * LocationListener Interface member variables.
     */
    android.location.LocationManager locationManager;
    android.location.Location deviceLocation;

    private long MIN_TIME_BW_UPDATES = 1;
    private float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    final static long MIN_TIME_INTERVAL = 60 * 1000L;

    /**
     * LocationManager Interface implementation.
     */

    /**
     *<p>This method sets the android context object for this class
     *
     * @param context Android context object
     */
    @Override
    public void setContext (Object context){
        this.context = (Context)context;
    }

    /**
     *<p>This method gets the actual device location data.
     *
     * @return Location interface object
     * @throws CantGetDeviceLocationException
     */
    @Override
    public Location getLocation() throws CantGetDeviceLocationException {
        Location location = null;
        try {
            locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

               // getting GPS status
            if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this,Looper.getMainLooper());
                //"GPS Enabled"
                if (locationManager != null)
                {
                    deviceLocation = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                    if (deviceLocation != null) {

                        location = new DeviceLocation(deviceLocation.getLatitude(),deviceLocation.getLongitude(),deviceLocation.getTime(),deviceLocation.getAltitude(),LocationProvider.GPS);

                    }
                    else{
                        /**
                         * I not get location device return an exception
                         */
                        throw  new CantGetDeviceLocationException("Not get GPS Enabled");
                    }
                }
                else{
                    /**
                     * I not get location device return an exception
                     */
                    throw  new CantGetDeviceLocationException("LocationManager is null");
                }
            }
            else {

                // GPS not enabled, get status from Network Provider
                if (locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {
                    locationManager.requestLocationUpdates( android.location.LocationManager.NETWORK_PROVIDER,  MIN_TIME_BW_UPDATES,  MIN_DISTANCE_CHANGE_FOR_UPDATES, this, Looper.getMainLooper());
                    // "Network"
                    if (locationManager != null) {
                        deviceLocation = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
                        if (deviceLocation != null) {

                            location = new DeviceLocation(deviceLocation.getLatitude(),deviceLocation.getLongitude(),deviceLocation.getTime(),deviceLocation.getAltitude(), LocationProvider.NETWORK);

                        }
                        else{
                            /**
                             * I not get location device return an exception
                             */
                            throw  new CantGetDeviceLocationException("Not get Network Enabled");
                        }
                    }else
                    {
                        /**
                         * I not get location device return an exception
                         */
                        throw  new CantGetDeviceLocationException("LocationManager is null");
                    }
                }


            }




        } catch (Exception e) {

            e.printStackTrace();

            /**
             * unexpected error
             */
            throw  new CantGetDeviceLocationException(CantGetDeviceLocationException.DEFAULT_MESSAGE,e,"","unexpected error");
        }

        return location;
    }

    /**
     * LocationListener Interface implementation.
     */

    @Override
    public void onLocationChanged( android.location.Location location) {

        if (location != null) {

            this.deviceLocation = location;

        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
