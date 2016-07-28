package com.bitdubai.fermat_cbp_core.layer.stock_transactions;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.stock_transactions.bank_money_destock.BankMoneyDestockPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.stock_transactions.bank_money_restock.BankMoneyRestockPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.stock_transactions.cash_money_destock.CashMoneyDestockPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.stock_transactions.cash_money_restock.CashMoneyRestockPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.stock_transactions.crypto_money_destock.CryptoMoneyDestockPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.stock_transactions.crypto_money_restock.CryptoMoneyRestockPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Franklin Marcano on 25/11/15.
 */
public class StockTransactionsLayer extends AbstractLayer {
    public StockTransactionsLayer() {
        super(Layers.STOCK_TRANSACTIONS);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new BankMoneyDestockPluginSubsystem());
            registerPlugin(new BankMoneyRestockPluginSubsystem());
            registerPlugin(new CashMoneyDestockPluginSubsystem());
            registerPlugin(new CashMoneyRestockPluginSubsystem());
            registerPlugin(new CryptoMoneyDestockPluginSubsystem());
            registerPlugin(new CryptoMoneyRestockPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }
}
