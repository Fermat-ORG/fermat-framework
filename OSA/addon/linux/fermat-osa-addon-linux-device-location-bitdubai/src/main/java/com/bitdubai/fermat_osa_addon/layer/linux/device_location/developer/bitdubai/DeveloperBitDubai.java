package com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.version_1.DeviceLocationSystemAddonRoot;

/**
 * The class <code>com.bitdubai.fermat_osa_addon.layer.linux.device_location.developer.bitdubai.DeveloperBitDubai</code>
 * Haves the logic of instantiation of all versions of Device Location Android Addon.
 * <p/>
 * Here we can choose between the different versions of the Device Location Addon.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DeveloperBitDubai extends AbstractAddonDeveloper {

    public DeveloperBitDubai(final AddonDeveloperReference addonDeveloperReference) {
        super(addonDeveloperReference);
    }

    @Override
    public void start() throws CantStartAddonDeveloperException {
        try {

            this.registerVersion(new DeviceLocationSystemAddonRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartAddonDeveloperException(e, "", "Error registering addon versions for the developer.");
        }
    }
}
