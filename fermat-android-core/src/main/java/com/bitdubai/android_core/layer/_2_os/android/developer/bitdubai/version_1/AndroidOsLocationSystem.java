package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1;

import android.content.Context;
import com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.version_1.DeviceLocationAndroidAddonRoot;
import com.bitdubai.fermat_api.layer.osa_android.LocationSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;

/**
 * Created by toshiba on 21/05/2015.
 */
public class AndroidOsLocationSystem implements LocationSystemOs {
    /**
     * LocationSystemOs interface member variables.
     */


    LocationManager locationManager;

    Context context;

    /**
     * Constructor
     */

    public AndroidOsLocationSystem(Context context) {

        this.context = context;
        try {
            DeviceLocationAndroidAddonRoot locationSystemOs = new DeviceLocationAndroidAddonRoot(context);
            locationSystemOs.start();
            this.locationManager = locationSystemOs.getLocationSystem();
        } catch (Exception e) {

            System.out.println("aca hubo un error");
            e.printStackTrace();
        }


    }
    /**
     * LocationSystemOs interface implementation.
     */

    @Override
    public LocationManager getLocationSystem() {
        return this.locationManager;
    }

}

