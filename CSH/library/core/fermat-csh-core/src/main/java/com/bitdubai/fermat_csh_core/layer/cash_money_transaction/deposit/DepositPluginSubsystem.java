package com.bitdubai.fermat_csh_core.layer.cash_money_transaction.deposit;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.deposit.developer.bitdubai.DeveloperBitDubai;


/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public class DepositPluginSubsystem extends AbstractPluginSubsystem {

    public DepositPluginSubsystem() {
        super(new PluginReference(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_DEPOSIT));
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




