package com.mati.fermat_osa_addon_android_broadcaster_system_bitdubai.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddonDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterVersionException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAddonDeveloperException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonDeveloperReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.AndroidCoreUtils;
import com.mati.fermat_osa_addon_android_broadcaster_system_bitdubai.structure.version_1.PluginBroadcasterSystemAndroidAddonRoot;

/**
 * The class <code>com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.PluginFileSystemDeveloperBitDubai</code>
 * Haves the logic of instantiation of all versions of Plugin File System Android Addon.
 * <p/>
 * Here we can choose between the different versions of the Plugin File System Addon.
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 27/10/2015.
 */
public class PluginBroadcasterSystemDeveloperBitDubai extends AbstractAddonDeveloper {


    private final AndroidCoreUtils androidCoreUtils;

    public PluginBroadcasterSystemDeveloperBitDubai(AndroidCoreUtils androidCoreUtils) {
        super(new AddonDeveloperReference(Developers.BITDUBAI));
        this.androidCoreUtils = androidCoreUtils;
    }

    @Override
    public void start() throws CantStartAddonDeveloperException {
        try {

            this.registerVersion(new PluginBroadcasterSystemAndroidAddonRoot(androidCoreUtils));

        } catch (CantRegisterVersionException e) {

            throw new CantStartAddonDeveloperException(e, "", "Error registering addon versions for the developer.");
        }
    }
}
