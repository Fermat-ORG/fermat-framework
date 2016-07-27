package com.bitdubai.fermat_cbp_core.layer.stock_transactions.cash_money_destock;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.cash_money_destock.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;


/**
 * Created by Franklin Marcano on 25/11/15.
 */
public class CashMoneyDestockPluginSubsystem extends AbstractPluginSubsystem {

    public CashMoneyDestockPluginSubsystem() {
        super(new PluginReference(Plugins.CASH_MONEY_DESTOCK));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("Exception: ").append(e.getMessage()).toString());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
