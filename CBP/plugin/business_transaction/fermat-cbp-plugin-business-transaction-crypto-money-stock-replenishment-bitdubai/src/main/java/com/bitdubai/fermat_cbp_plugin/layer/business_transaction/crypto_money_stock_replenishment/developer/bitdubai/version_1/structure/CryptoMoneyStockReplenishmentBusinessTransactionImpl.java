package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.crypto_money_stock_replenishment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CryptoCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.crypto_money_stock_replenishment.interfaces.CryptoMoneyStockReplenishment;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public class CryptoMoneyStockReplenishmentBusinessTransactionImpl implements BusinessTransaction, CryptoMoneyStockReplenishment{

    private final UUID transactionId;
    private final  String brokerPublicKey;
    private final  CurrencyType merchandiseCurrency;
    private final  float merchandiseAmount;
    private final  UUID executionTransactionId;
    private final  CryptoCurrencyType cryptoCurrencyType;
    private final  BusinessTransactionStatus transactionStatus;

    public CryptoMoneyStockReplenishmentBusinessTransactionImpl(
            UUID transactionId,
            String brokerPublicKey,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            UUID executionTransactionId,
            CryptoCurrencyType cryptoCurrencyType,
            BusinessTransactionStatus transactionStatus
    ){
        this.transactionId = transactionId;
        this.brokerPublicKey = brokerPublicKey;
        this.merchandiseCurrency = merchandiseCurrency;
        this.merchandiseAmount = merchandiseAmount;
        this.executionTransactionId = executionTransactionId;
        this.cryptoCurrencyType = cryptoCurrencyType;
        this.transactionStatus = transactionStatus;
    }

    @Override
    public UUID getTransactionId(){ return this.transactionId; }

    @Override
    public String getPublicKeyBroker(){ return this.brokerPublicKey; }

    @Override
    public CurrencyType getMerchandiseCurrency(){ return this.merchandiseCurrency; }

    @Override
    public float getMerchandiseAmount(){ return this.merchandiseAmount; }


    @Override
    public UUID getExecutionTransactionId(){ return this.executionTransactionId; }

    @Override
    public CryptoCurrencyType getCryptoCurrencyType(){ return this.cryptoCurrencyType; }

    @Override
    public BusinessTransactionStatus getStatus(){ return this.transactionStatus; }

}
