package com.bitdubai.fermat_bnk_core.layer.wallet_module.bank_money;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import com.bitdubai.fermat_bnk_plugin.layer.wallet_module.bank_money.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by memo on 07/12/15.
 */
public class WalletModulePluginSubsystem  extends AbstractPluginSubsystem {

    public WalletModulePluginSubsystem() {
        super(new PluginReference(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET_MODULE));
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
