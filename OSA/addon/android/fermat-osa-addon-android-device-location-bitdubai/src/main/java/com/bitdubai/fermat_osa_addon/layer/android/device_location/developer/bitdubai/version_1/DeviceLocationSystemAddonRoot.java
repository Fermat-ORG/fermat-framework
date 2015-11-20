package com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.version_1;

import android.content.Context;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationProvider;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;

/**
 * This addon handles a layer of Device Location representation.
 * Encapsulates all the necessary functions to retrieve the geolocation of the device.
 * <p/>
 * Created by Natalia 13/05/2015
 * Modified by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 */
public class DeviceLocationSystemAddonRoot extends AbstractAddon implements LocationManager, LocationListener {

    private Context context;

    public DeviceLocationSystemAddonRoot() {
        super(
                new AddonVersionReference(new Version()),
                true //indicates that you need the context
        );
    }

    @Override
    public void start() throws CantStartPluginException {

        if (this.getOsContext() != null && this.getOsContext() instanceof Context) {

            context = (Context) this.getOsContext();


        } else {
            throw new CantStartPluginException(
                    "osContext: "+this.getOsContext(),
                    "Context is not instance of Android Context or is null."
            );
        }

        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * LocationListener Interface member variables.
     */
    android.location.LocationManager locationManager;
    android.location.Location deviceLocation;

    private static final long MIN_TIME_BW_UPDATES = 1;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_TIME_INTERVAL = 60 * 1000L;

    /**
     * LocationManager Interface implementation.
     */

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
                locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this, Looper.getMainLooper());
                //"GPS Enabled"
                if (locationManager != null)
                {
                    deviceLocation = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                    if (deviceLocation != null) {

                        location = new DeviceLocation(deviceLocation.getLatitude(),deviceLocation.getLongitude(),deviceLocation.getTime(),deviceLocation.getAltitude(), LocationProvider.GPS);

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
