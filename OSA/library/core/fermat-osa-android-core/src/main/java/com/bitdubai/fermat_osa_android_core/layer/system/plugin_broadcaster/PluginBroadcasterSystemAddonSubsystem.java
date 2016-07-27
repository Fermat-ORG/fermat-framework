package com.bitdubai.fermat_osa_android_core.layer.system.plugin_broadcaster;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.AndroidCoreUtils;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.mati.fermat_osa_addon_android_broadcaster_system_bitdubai.structure.PluginBroadcasterSystemDeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PluginBroadcasterSystemAddonSubsystem extends AbstractAddonSubsystem {

    private final AndroidCoreUtils androidCoreUtils;

    public PluginBroadcasterSystemAddonSubsystem(AndroidCoreUtils androidCoreUtils) {
        super(new AddonReference(Addons.PLUGIN_BROADCASTER_SYSTEM));
        this.androidCoreUtils = androidCoreUtils;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new PluginBroadcasterSystemDeveloperBitDubai(androidCoreUtils));
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("Exception: ").append(e.getMessage()).toString());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
