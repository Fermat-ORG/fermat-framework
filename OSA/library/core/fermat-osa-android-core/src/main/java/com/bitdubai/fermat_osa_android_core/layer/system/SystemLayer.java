package com.bitdubai.fermat_osa_android_core.layer.system;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.AndroidCoreUtils;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterAddonException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_osa_android_core.layer.system.device_conectivity.DeviceConectivityAddonSubsystem;
import com.bitdubai.fermat_osa_android_core.layer.system.device_location.DeviceLocationAddonSubsystem;
import com.bitdubai.fermat_osa_android_core.layer.system.logger.LoggerAddonSubsystem;
import com.bitdubai.fermat_osa_android_core.layer.system.platform_database_system.PlatformDatabaseSystemAddonSubsystem;
import com.bitdubai.fermat_osa_android_core.layer.system.platform_file_system.PlatformFileSystemAddonSubsystem;
import com.bitdubai.fermat_osa_android_core.layer.system.plugin_broadcaster.PluginBroadcasterSystemAddonSubsystem;
import com.bitdubai.fermat_osa_android_core.layer.system.plugin_database_system.PluginDatabaseSystemAddonSubsystem;
import com.bitdubai.fermat_osa_android_core.layer.system.plugin_file_system.PluginFileSystemAddonSubsystem;
import com.bitdubai.fermat_osa_android_core.layer.system.plugin_hardware.PluginHardwareAddonSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class SystemLayer extends AbstractLayer {

    private AndroidCoreUtils androidCoreUtils;

    public SystemLayer() {
        super(Layers.SYSTEM);
    }

    public void setAndroidCoreUtils(AndroidCoreUtils androidCoreUtils) {
        this.androidCoreUtils = androidCoreUtils;
    }

    public void start() throws CantStartLayerException {

        /* register addons here */

        try {

            registerAddon(new DeviceLocationAddonSubsystem());
            registerAddon(new LoggerAddonSubsystem());
            registerAddon(new PlatformDatabaseSystemAddonSubsystem());
            registerAddon(new PluginDatabaseSystemAddonSubsystem());
            registerAddon(new PlatformFileSystemAddonSubsystem());
            registerAddon(new PluginFileSystemAddonSubsystem());
            registerAddon(new PluginBroadcasterSystemAddonSubsystem(androidCoreUtils));
            registerAddon(new PluginHardwareAddonSubsystem());
            registerAddon(new DeviceConectivityAddonSubsystem());

        } catch (CantRegisterAddonException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register an addon in android layer of osa platform."
            );
        }
    }

}
