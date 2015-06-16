package com.bitdubai.fermat_api.layer.osa_android;

import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;

/**
 * Created by natalia on 21/05/2015.
 */
public interface LocationSystemOs {

    public LocationManager getLocationSystem();

    public void setContext (Object context);
}
