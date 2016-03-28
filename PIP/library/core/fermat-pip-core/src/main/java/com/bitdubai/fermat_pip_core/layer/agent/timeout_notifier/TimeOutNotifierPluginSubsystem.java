package com.bitdubai.fermat_pip_core.layer.agent.timeout_notifier;

import com.bitbudai.fermat_pip_plugin.layer.agent.timeout_notifier.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by rodrigo on 3/26/16.
 */
public class TimeOutNotifierPluginSubsystem extends AbstractPluginSubsystem {

    public TimeOutNotifierPluginSubsystem() {
        super(new PluginReference(Plugins.TIMEOUT_NOTIFIER));
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
