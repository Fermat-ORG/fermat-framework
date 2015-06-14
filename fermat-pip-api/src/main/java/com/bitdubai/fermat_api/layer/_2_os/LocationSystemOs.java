package com.bitdubai.fermat_api.layer._2_os;

import com.bitdubai.fermat_api.layer._2_os.location_system.LocationManager;

/**
 * Created by natalia on 21/05/2015.
 */
public interface LocationSystemOs {

    public LocationManager getLocationSystem();

    public void setContext (Object context);
}
