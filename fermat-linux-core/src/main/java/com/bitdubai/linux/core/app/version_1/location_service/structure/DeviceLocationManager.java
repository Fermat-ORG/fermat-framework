package com.bitdubai.linux.core.app.version_1.location_service.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationSource;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.linux.core.app.version_1.location_service.exceptions.CantAcquireLocationException;
import com.bitdubai.linux.core.app.version_1.location_service.utils.LocationProvider;

/**
 * The class <code>com.bitdubai.linux.core.app.version_1.location_service.structure.DeviceLocationManager</code>

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

    private final ErrorManager           errorManager          ;
    private final PluginVersionReference pluginVersionReference;

    private Location lastKnownLocation;

    public DeviceLocationManager(final ErrorManager           errorManager          ,
                                 final PluginVersionReference pluginVersionReference) {

        this.errorManager           = errorManager          ;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public Location getLocation(final LocationSource source) throws CantGetDeviceLocationException {

        lastKnownLocation = getLocation();

        return lastKnownLocation;
    }

    @Override
    public Location getLastKnownLocation() throws CantGetDeviceLocationException {
        return lastKnownLocation;
    }

    @Override
    public Location getLocation() throws CantGetDeviceLocationException {

        try {

            lastKnownLocation = LocationProvider.acquireLocationThroughIP();

            return lastKnownLocation;

        } catch (CantAcquireLocationException cantAcquireLocationException) {

            this.errorManager.reportUnexpectedPluginException(
                    this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    cantAcquireLocationException
            );
            throw new CantGetDeviceLocationException(
                    cantAcquireLocationException,
                    "",
                    "There was a problem trying to acquire the location."
            );
        } catch (Exception exception) {

            this.errorManager.reportUnexpectedPluginException(
                    this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    exception
            );
            throw new CantGetDeviceLocationException(
                    exception,
                    "",
                    "Unhandled error."
            );
        }
    }
}
