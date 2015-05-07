package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.device_location;

import android.content.Context;

import android.location.LocationListener;
import android.os.Bundle;

import com.bitdubai.fermat_api.CantGetDeviceLocationException;
import com.bitdubai.fermat_api.layer._2_os.device_location.Location;
import com.bitdubai.fermat_api.layer._2_os.device_location.LocationManager;
import com.bitdubai.fermat_api.layer._2_os.device_location.LocationProvider;

/**
 * Created by Natalia on 30/04/2015.
 */
public class AndroidLocationManager implements LocationManager,LocationListener {

    /**
     * LocationManager Interface member variables.
     */
    private Context context;
    private double lat;
    private double lng;

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

    @Override
   public void setContext (Object context){
        this.context = (Context)context;
    }

    @Override
    public Location getLocation() throws CantGetDeviceLocationException {
        com.bitdubai.fermat_api.layer._2_os.device_location.Location location = null;
        try {
            locationManager = (android.location.LocationManager) context.getSystemService(context.LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                // First get location from Network Provider
                if (isNetworkEnabled) {
                   locationManager.requestLocationUpdates( android.location.LocationManager.NETWORK_PROVIDER,  MIN_TIME_BW_UPDATES,  MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                   // "Network"
                    if (locationManager != null) {
                        deviceLocation = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
                        if (deviceLocation != null) {

                            location = new AndroidLocation(deviceLocation.getLatitude(),deviceLocation.getLongitude(),deviceLocation.getTime(),deviceLocation.getAltitude(),LocationProvider.NETWORK);

                        }
                        else{
                            /**
                             * I not get location device return an exception
                             */
                            throw  new CantGetDeviceLocationException();
                        }
                    }else
                    {
                        /**
                         * I not get location device return an exception
                         */
                        throw  new CantGetDeviceLocationException();
                    }
                }
                //get the location by gps
                if (isGPSEnabled) {
                    if (deviceLocation == null) {
                        locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        //"GPS Enabled"
                        if (locationManager != null)
                        {
                            deviceLocation = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                            if (deviceLocation != null) {

                                location = new AndroidLocation(deviceLocation.getLatitude(),deviceLocation.getLongitude(),deviceLocation.getTime(),deviceLocation.getAltitude(),LocationProvider.GPS);

                            }
                            else{
                                /**
                                 * I not get location device return an exception
                                 */
                                throw  new CantGetDeviceLocationException();
                            }
                        }
                        else{
                            /**
                             * I not get location device return an exception
                             */
                            throw  new CantGetDeviceLocationException();
                        }
                    }
                }
            }

        } catch (Exception e) {
            /**
             * unexpected error
             */
            throw  new CantGetDeviceLocationException();
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
