package com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.version_1;

import android.content.Context;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededOsContext;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_osa_addon.layer.android.device_location.developer.bitdubai.version_1.structure.DeviceLocationManager;

/**
 * This addon handles a layer of Device Location representation.
 * Encapsulates all the necessary functions to retrieve the geolocation of the device.
 * <p/>
 * Created by Natalia 13/05/2015
 * Modified by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 */
public class DeviceLocationSystemAddonRoot extends AbstractAddon {

    @NeededOsContext
    private Context context;

    private FermatManager fermatManager;

    public DeviceLocationSystemAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            fermatManager = new DeviceLocationManager(context);

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Device Location Manager starting.", "Unhandled Exception trying to start the Device Location manager.");
        }
    }

    public final FermatManager getManager() {
        return fermatManager;
    }
}
