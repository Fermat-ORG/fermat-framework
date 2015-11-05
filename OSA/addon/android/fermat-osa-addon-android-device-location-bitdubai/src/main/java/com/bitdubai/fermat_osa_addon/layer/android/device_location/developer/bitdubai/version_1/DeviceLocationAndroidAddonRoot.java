package com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.OperativeSystems;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.LocationSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.version_1.structure.DeviceLocationManager;


import android.content.Context;

/**
 * This addon handles a layer of Device Location representation.
 * Encapsulates all the necessary functions to retrieve the geolocation of the device.
 * <p/>
 * Created by Natalia 13/05/2015
 * Modified by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 */
public class DeviceLocationAndroidAddonRoot extends AbstractAddon implements LocationSystemOs {

    private Context         context              ;
    private LocationManager locationSystemManager;

    public DeviceLocationAndroidAddonRoot() {
        super(
                new AddonVersionReference(new Version()),
                true
        );
    }

    public DeviceLocationAndroidAddonRoot(Context context) {
        super(
                new AddonVersionReference(new Version()),
                true
        );

        this.context = context;
    }

    @Override
    public void start() throws CantStartPluginException {

        locationSystemManager = new DeviceLocationManager(context);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public LocationManager getLocationSystem() {
        return locationSystemManager;
    }

}
