package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1;

import android.content.Context;

import com.bitdubai.fermat_api.layer.osa_android.LocationSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.version_1.DeviceLocationAndroidAddonRoot;

/**
 * Created by toshiba on 21/05/2015.
 */
public class AndroidOsLocationSystem implements LocationSystemOs {
    /**
     * LocationSystemOs interface member variables.
     */


    LocationManager locationManager;

    LocationSystemOs locationSystemOs;

    Context context;

    /**
     * Constructor
     */

    public AndroidOsLocationSystem(Context context) {

        this.context = context;

        locationSystemOs = new DeviceLocationAndroidAddonRoot();
        this.locationManager = locationSystemOs.getLocationSystem();


    }
    /**
     * LocationSystemOs interface implementation.
     */

    @Override
    public LocationManager getLocationSystem() {
        return this.locationManager;
    }

}

