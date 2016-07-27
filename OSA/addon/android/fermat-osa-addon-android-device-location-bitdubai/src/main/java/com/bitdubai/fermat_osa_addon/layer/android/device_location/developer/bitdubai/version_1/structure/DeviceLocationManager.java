package com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.version_1.structure;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;

import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;

/**
 * Created by Natalia on 21/05/2015.
 * <p/>
 * This addon handles a layer of Device Location representation.
 * Encapsulates all the necessary functions to retrieve the geolocation of the device.
 * <p/>
 * * * *
 */

public class DeviceLocationManager implements LocationManager, LocationListener {

    private final Context context;

    public DeviceLocationManager(final Context context) {
        this.context = context;
    }

    private Location lastKnownLocation;

    @Override
    public Location getLocation(final LocationSource source) throws CantGetDeviceLocationException {

        lastKnownLocation = getLocation();

        return lastKnownLocation;
    }

    @Override
    public Location getLastKnownLocation() throws CantGetDeviceLocationException {
        if (lastKnownLocation != null)
            return lastKnownLocation;
        else
            return getLocation();
    }

    /**
     * LocationListener Interface member variables.
     */
    android.location.LocationManager locationManager;
    android.location.Location deviceLocation;

    private static final long MIN_TIME_BW_UPDATES = 1;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;

    /**
     * LocationManager Interface implementation.
     */

    /**
     * <p>This method gets the actual device location data.
     *
     * @return Location interface object
     * @throws CantGetDeviceLocationException
     */
    @Override
    public Location getLocation() throws CantGetDeviceLocationException {
        Location location = null;

        try {
            try {
                locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                // getting GPS status
                if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this, Looper.getMainLooper());
                    //"GPS Enabled"
                    if (locationManager != null) {
                        deviceLocation = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                        if (deviceLocation != null) {

                            location = new DeviceLocation(deviceLocation.getLatitude(), deviceLocation.getLongitude(), deviceLocation.getTime(), deviceLocation.getAltitude(), LocationSource.GPS);

                        } else {
                            /**
                             * I not get location device return an exception
                             */
                            throw new CantGetDeviceLocationException("Not get GPS Enabled");
                        }
                    } else {
                        /**
                         * I not get location device return an exception
                         */
                        throw new CantGetDeviceLocationException("LocationManager is null");
                    }
                } else {

                    // GPS not enabled, get status from Network Provider
                    if (locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    Activity#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for Activity#requestPermissions for more details.
                                    return null;
                                }
                            }
                        }

                        locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this, Looper.getMainLooper());
                        // "Network"
                        if (locationManager != null) {
                            deviceLocation = locationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
                            if (deviceLocation != null) {

                                location = new DeviceLocation(deviceLocation.getLatitude(), deviceLocation.getLongitude(), deviceLocation.getTime(), deviceLocation.getAltitude(), LocationSource.NETWORK);

                            } else {
                                /**
                                 * I not get location device return an exception
                                 */
                                throw new CantGetDeviceLocationException("Not get Network Enabled");
                            }
                        } else {
                            /**
                             * I not get location device return an exception
                             */
                            throw new CantGetDeviceLocationException("LocationManager is null");
                        }
                    }


                }


            } catch (Exception e) {

                /**
                 * unexpected error
                 */
                throw new CantGetDeviceLocationException(CantGetDeviceLocationException.DEFAULT_MESSAGE, e, "", "unexpected error");
            }
        } catch (Exception e) {
            // TODO manage this kind of exception in a better way

            if (lastKnownLocation != null)
                return lastKnownLocation;
            else
                location = new DeviceLocation(0.0, 0.0, System.currentTimeMillis(), 0.0, LocationSource.UNKNOWN);
        }
        lastKnownLocation = location;
        if (location == null) {
            location = new DeviceLocation(0.0, 0.0, System.currentTimeMillis(), 0.0, LocationSource.UNKNOWN);
        }
        return location;
    }

    /**
     * LocationListener Interface implementation.
     */

    @Override
    public void onLocationChanged(android.location.Location location) {

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
