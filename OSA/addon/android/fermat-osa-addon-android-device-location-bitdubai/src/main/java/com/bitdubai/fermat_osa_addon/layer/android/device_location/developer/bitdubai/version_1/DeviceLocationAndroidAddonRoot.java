package com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.enums.OperativeSystems;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.LocationSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.version_1.structure.DeviceLocationManager;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.content.Context;

import org.w3c.dom.events.EventListener;

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
                OperativeSystems.ANDROID
        );
    }

    public DeviceLocationAndroidAddonRoot(Context context) {
        super(
                new AddonVersionReference(new Version()),
                OperativeSystems.ANDROID
        );

        this.context = context;
    }

    @Override
    public void start() throws CantStartPluginException {

        /*

        TODO generate an annotation to assign OS CONTEXT.

        if (this.getOsContext() != null && this.getOsContext() instanceof Context) {

            context = (Context) this.getOsContext();


        } else {
            throw new CantStartPluginException(
                    "osContext: "+this.getOsContext(),
                    "Context is not instance of Android Context or is null."
            );
        }*/

        locationSystemManager = new DeviceLocationManager(context);

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public LocationManager getLocationSystem() {
        return locationSystemManager;
    }

}
