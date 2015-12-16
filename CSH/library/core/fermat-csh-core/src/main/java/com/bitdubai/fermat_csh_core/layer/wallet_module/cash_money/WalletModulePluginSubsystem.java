package com.bitdubai.fermat_csh_core.layer.wallet_module.cash_money;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_csh_plugin.layer.wallet_module.cash_money.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Alejandro Bicelis on 12/9/2015.
 */
public class WalletModulePluginSubsystem extends AbstractPluginSubsystem {

    public WalletModulePluginSubsystem() {
        super(new PluginReference(Plugins.BITDUBAI_CSH_MONEY_WALLET_MODULE));
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
