package com.bitdubai.desktop_core.layer._2_os.desktop.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.osa_android.LocationSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;

/**
 *
 * @author Created by Matias
 */
public class DesktopOsLocationSystem implements LocationSystemOs{
    
    /**
     * LocationSystemOs interface member variables.
     */


    LocationManager locationManager;

    LocationSystemOs locationSystemOs;


    /**
     * Constructor
     */

    public DesktopOsLocationSystem() {

        locationSystemOs =null; // new DeviceLocationOsAddonRoot();
        this.locationManager =null;// locationSystemOs.getLocationSystem();


    }
    /**
     * LocationSystemOs interface implementation.
     */

    @Override
    public LocationManager getLocationSystem()
    {
        return this.locationManager;
    }

    
    /**
     *  Method unavailable for desktop
     * @param context 
     */
    @Override
    public void setContext(Object context) {

        //this.context = (Context) context;
        //this.locationManager.setContext(this.context);



    }
}
