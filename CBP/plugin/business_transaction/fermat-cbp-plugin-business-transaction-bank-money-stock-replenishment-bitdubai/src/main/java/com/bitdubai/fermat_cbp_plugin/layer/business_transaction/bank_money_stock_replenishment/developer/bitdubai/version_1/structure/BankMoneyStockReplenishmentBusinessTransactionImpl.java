package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.bank_money_stock_replenishment.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.bank_money_stock_replenishment.interfaces.BankMoneyStockReplenishment;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public class BankMoneyStockReplenishmentBusinessTransactionImpl implements BankMoneyStockReplenishment{

    private UUID transactionId;
    private KeyPair keyPairBroker;
    private CurrencyType merchandiseCurrency;
    private float merchandiseAmount;
    private UUID executionTransactionId;
    private BankCurrencyType bankCurrencyType;
    private BankOperationType bankOperationType;
    private BusinessTransactionStatus transactionStatus;

    public BankMoneyStockReplenishmentBusinessTransactionImpl(
            UUID transactionId,
            KeyPair keyPairBroker,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            UUID executionTransactionId,
            BankCurrencyType bankCurrencyType,
            BankOperationType bankOperationType,
            BusinessTransactionStatus transactionStatus
    ){
        this.transactionId = transactionId;
        this.keyPairBroker = keyPairBroker;
        this.merchandiseCurrency = merchandiseCurrency;
        this.merchandiseAmount = merchandiseAmount;
        this.executionTransactionId = executionTransactionId;
        this.bankCurrencyType = bankCurrencyType;
        this.bankOperationType = bankOperationType;
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
    public BankCurrencyType getBankCurrencyType(){ return this.bankCurrencyType; }
    public void setBankCurrencyType(BankCurrencyType currencyType){ this.bankCurrencyType = currencyType; }

    @Override
    public BankOperationType getBankOperationType(){ return this.bankOperationType; }
    public void setBankOperationType(BankOperationType operationType){ this.bankOperationType = operationType; }

    @Override
    public BusinessTransactionStatus getStatus(){ return this.transactionStatus; }
    public void setStatus(BusinessTransactionStatus status){ this.transactionStatus = status; }

}
