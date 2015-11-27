package com.bitdubai.fermat_cbp_core.layer.stock_transactions.crypto_money_restock;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.crypto_money_restock.developer.bitdubai.DeveloperBitDubai;


/**
 * Created by Franklin Marcano on 25/11/15.
 */
public class CryptoMoneyRestockPluginSubsystem extends AbstractPluginSubsystem {

    public CryptoMoneyRestockPluginSubsystem() {
        super(new PluginReference(Plugins.CRYPTO_MONEY_RESTOCK));
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
