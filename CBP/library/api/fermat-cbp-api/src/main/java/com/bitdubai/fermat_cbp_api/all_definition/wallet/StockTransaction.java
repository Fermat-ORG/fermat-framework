package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.OriginTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by jorge on 30-09-2015.
 */
public interface StockTransaction extends Serializable {

    /**
     * The method <code>getTransactionId</code> returns the id of the transaction
     *
     * @return an UUID of the transaction
     */
    UUID getTransactionId();

    /**
     * The method <code>getBalanceType</code> returns balance type
     *
     * @return BalanceType
     */
    BalanceType getBalanceType();

    /**
     * The method <code>getTransactionType</code> returns the transaction type
     *
     * @return TransactionType
     */
    TransactionType getTransactionType();

    /**
     * The method <code>getMoneyType</code> returns the currency type
     *
     * @return MoneyType
     */
    MoneyType getMoneyType();

    /**
     * The method <code>getMerchandise</code> returns the merchandise
     *
     * @return Currency
     */
    Currency getMerchandise();

    /**
     * The method <code>getWalletPublicKey</code> returns public key of the wallet
     *
     * @return an String of the wallet public key
     */
    String getWalletPublicKey();

    /**
     * The method <code>getBrokerPublicKey</code> returns the public key of the broker
     *
     * @return an String of the broker public key
     */
    String getBrokerPublicKey();

    /**
     * The method <code>getAmount</code> returns the amount
     *
     * @return a BigDecimal of the amount
     */
    BigDecimal getAmount();

    /**
     * The method <code>getTimestamp</code> returns the timeStamp
     *
     * @return a long of the timeStamp
     */
    long getTimestamp();

    /**
     * The method <code>getMemo</code> returns the memo
     *
     * @return an String of the memo
     */
    String getMemo();

    /**
     * The method <code>getPriceReference</code> returns the price reference
     *
     * @return a BigDecimal of the price reference
     */
    BigDecimal getPriceReference();

    /**
     * The method <code>getOriginTransaction</code> returns the origin transaction
     *
     * @return OriginTransaction
     */
    OriginTransaction getOriginTransaction();

    /**
     * The method <code>getOriginTransactionId</code> returns the origin transaction
     *
     * @return String
     */
    String getOriginTransactionId();

    /**
     * The method <code>getSeen</code> returns the origin transaction
     *
     * @return boolean
     */
    boolean getSeen();
}
