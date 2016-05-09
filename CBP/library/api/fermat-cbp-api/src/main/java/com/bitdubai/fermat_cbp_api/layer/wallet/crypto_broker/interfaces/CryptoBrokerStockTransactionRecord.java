package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;

import java.math.BigDecimal;

/**
 * Created by Yordin Alayn on 02.10.15.
 */
public interface CryptoBrokerStockTransactionRecord extends StockTransaction {

    /**
     * The method <code>getRunningBookBalance</code> returns running book balance of the CryptoBrokerStockTransactionRecord
     *
     * @return a BigDecimal of the running book balance
     */
    BigDecimal getRunningBookBalance();

    /**
     * The method <code>getRunningBookBalance</code> returns running available balance of the CryptoBrokerStockTransactionRecord
     *
     * @return a BigDecimal of the running available balance
     */
    BigDecimal getRunningAvailableBalance();

}