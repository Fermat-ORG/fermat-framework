package com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.structure.DeviceLocationManager;

/**
 * This addon handles a layer of Device Location representation.
 * Encapsulates all the necessary functions to retrieve the geolocation of the device.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeviceLocationSystemAddonRoot extends AbstractAddon {

    private FermatManager fermatManager;

    public DeviceLocationSystemAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    @Override
    public final void start() throws CantStartPluginException {

        try {

            fermatManager = new DeviceLocationManager();

            super.start();

        } catch (final Exception e) {

            throw new CantStartPluginException(e, "Device Location Manager starting.", "Unhandled Exception trying to start the Device Location manager.");
        }
    }

    public final FermatManager getManager() {
        return fermatManager;
    }
}
