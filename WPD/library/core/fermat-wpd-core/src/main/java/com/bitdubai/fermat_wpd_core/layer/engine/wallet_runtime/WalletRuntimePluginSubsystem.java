package com.bitdubai.fermat_wpd_core.layer.engine.wallet_runtime;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_wpd_plugin.layer.engine.wallet_runtime.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletRuntimePluginSubsystem extends AbstractPluginSubsystem {

    public WalletRuntimePluginSubsystem() {
        super(new PluginReference(Plugins.WALLET_RUNTIME));
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
