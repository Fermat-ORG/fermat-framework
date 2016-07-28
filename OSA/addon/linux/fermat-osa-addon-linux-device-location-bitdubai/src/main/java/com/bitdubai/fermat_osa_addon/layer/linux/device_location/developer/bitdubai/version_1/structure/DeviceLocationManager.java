package com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.exceptions.CantAcquireLocationException;
import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.utils.LocationProvider;

/**
 * The class <code>com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.structure.DeviceLocationManager</code>
 * <p/>
 * This addon handles a layer of Device Location representation.
 * Encapsulates all the necessary functions to retrieve the geolocation of the device.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeviceLocationManager implements LocationManager {

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

    @Override
    public Location getLocation() throws CantGetDeviceLocationException {

        try {

            lastKnownLocation = LocationProvider.acquireLocationThroughIP();

            return lastKnownLocation;

        } catch (CantAcquireLocationException cantAcquireLocationException) {

            throw new CantGetDeviceLocationException(
                    cantAcquireLocationException,
                    "",
                    "There was a problem trying to acquire the location."
            );
        } catch (Exception exception) {

            throw new CantGetDeviceLocationException(
                    exception,
                    "",
                    "Unhandled error."
            );
        }
    }
}
