package com.bitdubai.fermat_pip_core.layer.sub_app_module.android_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_pip_plugin.layer.android_core_module.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by natalia on 26/01/16.
 */
public class AndroidCorePluginSubsystem extends AbstractPluginSubsystem {

    public AndroidCorePluginSubsystem() {
        super(new PluginReference(Plugins.ANDROID_CORE));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
