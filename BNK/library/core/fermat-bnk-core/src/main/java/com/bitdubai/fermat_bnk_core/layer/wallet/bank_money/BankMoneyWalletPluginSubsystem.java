package com.bitdubai.fermat_bnk_core.layer.wallet.bank_money;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_bnk_plugin.layer.wallet.bank_money.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by memo on 25/11/15.
 */
public class BankMoneyWalletPluginSubsystem extends AbstractPluginSubsystem {

    public BankMoneyWalletPluginSubsystem() {
        super(new PluginReference(Plugins.BITDUBAI_BNK_BANK_MONEY_WALLET));
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
