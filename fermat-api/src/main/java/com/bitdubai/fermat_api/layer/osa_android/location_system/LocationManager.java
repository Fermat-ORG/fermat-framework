package com.bitdubai.fermat_api.layer.osa_android.location_system;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;

/**
 * <p>The public interfaces <code>LocationManager</code> is a interface
 * that define the methods to get the geolocation of the device.
 * <p/>
 * <p/>
 * Created by nattyco on 30/04/2015.
 * Modified by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 *
 * @version 1.0.0
 */
public interface LocationManager extends FermatManager {

    /**
     * Through the method <code>getLocation</code> we can get the must updated coordinates
     * of the device in which we're working.
     *
     * @return an instance of location with the coordinates.
     * @throws CantGetDeviceLocationException if something goes wrong.
     */
    Location getLocation() throws CantGetDeviceLocationException;

    /**
     * Through the method <code>getLocation</code> we can get the must updated coordinates
     * of the device in which we're working.
     *
     * @param source of where we want to get the location.
     * @return an instance of location with the coordinates.
     * @throws CantGetDeviceLocationException if something goes wrong.
     */
    Location getLocation(LocationSource source) throws CantGetDeviceLocationException;

    /**
     * Through the method <code>getLastKnownLocation</code> you can get the last known coordinates found for
     * the device in which we're working.
     *
     * @return an instance of location with the gps coordinates.
     * @throws CantGetDeviceLocationException if something goes wrong.
     */
    Location getLastKnownLocation() throws CantGetDeviceLocationException;

}
