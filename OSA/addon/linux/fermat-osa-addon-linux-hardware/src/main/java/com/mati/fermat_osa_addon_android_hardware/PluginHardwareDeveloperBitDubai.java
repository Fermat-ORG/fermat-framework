package com.mati.fermat_osa_addon_android_hardware;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.mati.fermat_osa_addon_android_hardware.version_1.PluginHardwareAndroidAddonRoot;

/**
 * The class <code>com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.DeveloperBitDubai</code>
 * Haves the logic of instantiation of all versions of Plugin Database System Android Addon.
 * <p/>
 * Here we can choose between the different versions of the Plugin Database System Addon.
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class PluginHardwareDeveloperBitDubai extends AbstractAddonDeveloper {

    public PluginHardwareDeveloperBitDubai() {
        super(new AddonDeveloperReference(Developers.BITDUBAI));
    }

    @Override
    public void start() throws CantStartAddonDeveloperException {
        try {

            this.registerVersion(new PluginHardwareAndroidAddonRoot());

        } catch (CantRegisterVersionException e) {

            throw new CantStartAddonDeveloperException(e, "", "Error registering addon versions for the developer.");
        }
    }
}
