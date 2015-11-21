package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.cash_money_stock_replenishment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.cash_money_stock_replenishment.interfaces.CashMoneyStockReplenishment;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public class CashMoneyStockReplenishmentBusinessTransactionImpl implements BusinessTransaction, CashMoneyStockReplenishment{

    private UUID transactionId;
    private KeyPair keyPairBroker;
    private CurrencyType merchandiseCurrency;
    private float merchandiseAmount;
    private UUID executionTransactionId;
    private CashCurrencyType cashCurrencyType;
    private BusinessTransactionStatus transactionStatus;

    public CashMoneyStockReplenishmentBusinessTransactionImpl(
            UUID transactionId,
            KeyPair keyPairBroker,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            UUID executionTransactionId,
            CashCurrencyType cashCurrencyType,
            BusinessTransactionStatus transactionStatus
    ){
        this.transactionId = transactionId;
        this.keyPairBroker = keyPairBroker;
        this.merchandiseCurrency = merchandiseCurrency;
        this.merchandiseAmount = merchandiseAmount;
        this.executionTransactionId = executionTransactionId;
        this.cashCurrencyType = cashCurrencyType;
        this.transactionStatus = transactionStatus;
    }

    @Override
    public UUID getTransactionId(){ return this.transactionId; }
    public void setIdTransaction(UUID id) { this.transactionId = id; }

    @Override
    public String getPublicKeyBroker(){ return this.keyPairBroker.getPublicKey(); }
    public  void setPublicKeyBroker(String publicKey){ this.keyPairBroker = keyPairBroker; }

    @Override
    public CurrencyType getMerchandiseCurrency(){ return this.merchandiseCurrency; }
    public void setMerchandiseCurrency(CurrencyType merchandise){ this.merchandiseCurrency = merchandise; }

    @Override
    public float getMerchandiseAmount(){ return this.merchandiseAmount; }
    public void setMerchandiseAmount(float amount){ this.merchandiseAmount = amount; }

    @Override
    public UUID getExecutionTransactionId(){ return this.executionTransactionId; }
    public void setExecutionTransactionId(UUID execution){ this.executionTransactionId = execution; }

    @Override
    public CashCurrencyType getCashCurrencyType(){ return this.cashCurrencyType; }
    public void setCashCurrencyType(CashCurrencyType currencyType){ this.cashCurrencyType = currencyType; }

    @Override
    public BusinessTransactionStatus getStatus(){ return this.transactionStatus; }
    public void setStatus(BusinessTransactionStatus status){ this.transactionStatus = status; }

}
