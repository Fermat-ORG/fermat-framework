package com.bitdubai.fermat_osa_android_core.layer.system.plugin_hardware;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractAddonSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.mati.fermat_osa_addon_android_hardware.PluginHardwareDeveloperBitDubai;


/**
 * Created by Furszy
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PluginHardwareAddonSubsystem extends AbstractAddonSubsystem {

    public PluginHardwareAddonSubsystem() {
        super(new AddonReference(Addons.HARDWARE));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new PluginHardwareDeveloperBitDubai());
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("Exception: ").append(e.getMessage()).toString());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
