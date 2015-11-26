package com.bitdubai.fermat_csh_core.layer.cash_money_transaction.hold;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_csh_plugin.layer.cash_money_transaction.hold.developer.bitdubai.DeveloperBitDubai;


/**
 * Created by Alejandro Bicelis on 11/25/2015.
 */
public class HoldPluginSubsystem extends AbstractPluginSubsystem {

    public HoldPluginSubsystem() {
        super(new PluginReference(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD));
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




