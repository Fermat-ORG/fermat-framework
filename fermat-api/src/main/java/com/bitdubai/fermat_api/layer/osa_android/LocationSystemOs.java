package com.bitdubai.fermat_api.layer.osa_android;

import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;

/**
 * Created by natalia on 21/05/2015.
 * Modified by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 */
public interface LocationSystemOs {

    /**
     * Throw the method <code>getLocationSystem</code> you can get a location manager
     * to know the gps coordinates of the device.
     *
     * @return a instance of location manager.
     */
    LocationManager getLocationSystem();

}
