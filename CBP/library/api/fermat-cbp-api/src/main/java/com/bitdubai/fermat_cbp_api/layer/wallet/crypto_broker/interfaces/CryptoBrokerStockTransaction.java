package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;

import java.math.BigDecimal;

/**
 * Created by Franklin  Marcano 03.12.2015
 */
public interface CryptoBrokerStockTransaction extends StockTransaction {

    /**
     * The method <code>getRunningBookBalance</code> returns the running book balance of the crypto broker stock transaction
     *
     * @return an String of the transaction id
     */
    BigDecimal getRunningBookBalance();

    /**
     * The method <code>getRunningAvailableBalance</code> returns the running available balance of the crypto broker stock transaction
     *
     * @return an String of the transaction id
     */
    BigDecimal getRunningAvailableBalance();

    /**
     * The method <code>getPreviousBookBalance</code> returns the previous book balance of the crypto broker stock transaction
     *
     * @return an String of the transaction id
     */
    BigDecimal getPreviousBookBalance();

    /**
     * The method <code>getPreviousAvailableBalance</code> returns the previous available book balance of the crypto broker stock transaction
     *
     * @return an String of the transaction id
     */
    BigDecimal getPreviousAvailableBalance();
}