package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_crypto_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CryptoCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_crypto_purchase.interfaces.CustomerBrokerCryptoPurchase;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public class CustomerBrokerCryptoPurchaseBusinessTransactionImpl implements BusinessTransaction, CustomerBrokerCryptoPurchase{

    private UUID transactionId;
    private UUID contractId;
    private KeyPair keyPairBroker;
    private KeyPair keyPairCustomer;
    private UUID paymentTransactionId;
    private CurrencyType paymentCurrency;
    private CurrencyType merchandiseCurrency;
    private float merchandiseAmount;
    private UUID executionTransactionId;
    private CryptoCurrencyType cryptoCurrencyType;
    private BusinessTransactionStatus transactionStatus;

    public CustomerBrokerCryptoPurchaseBusinessTransactionImpl(
            UUID transactionId,
            UUID contractId,
            KeyPair keyPairBroker,
            KeyPair keyPairCustomer,
            UUID paymentTransactionId,
            CurrencyType paymentCurrency,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            UUID executionTransactionId,
            CryptoCurrencyType cryptoCurrencyType,
            BusinessTransactionStatus transactionStatus
    ){
        this.transactionId = transactionId;
        this.contractId = contractId;
        this.keyPairBroker = keyPairBroker;
        this.keyPairCustomer = keyPairCustomer;
        this.paymentTransactionId = paymentTransactionId;
        this.paymentCurrency = paymentCurrency;
        this.merchandiseCurrency = merchandiseCurrency;
        this.merchandiseAmount = merchandiseAmount;
        this.executionTransactionId = executionTransactionId;
        this.cryptoCurrencyType = cryptoCurrencyType;
        this.transactionStatus = transactionStatus;
    }

    @Override
    public UUID getTransactionId(){ return this.transactionId; }
    public void setIdTransaction(UUID id) { this.transactionId = id; }

    @Override
    public UUID getContractId(){ return this.contractId; }
    public void setContractId(UUID id) { this.contractId = id; }

    @Override
    public String getPublicKeyBroker(){ return this.keyPairBroker.getPublicKey(); }
    public  void setPublicKeyBroker(String publicKey){ this.keyPairBroker = keyPairBroker; }

    @Override
    public String getPublicKeyCustomer(){ return this.keyPairCustomer.getPublicKey(); }
    public  void setPublicKeyCustomer(String publicKey){ this.keyPairCustomer = keyPairCustomer; }

    @Override
    public UUID getPaymentTransactionId(){ return this.paymentTransactionId; }
    public void setPaymentTransactionId(UUID paymentId){ this.paymentTransactionId = paymentId; }

    @Override
    public CurrencyType getPaymentCurrency(){ return this.paymentCurrency; }
    public void setPaymentCurrency(CurrencyType payment){ this.paymentCurrency = payment; }

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
    public CryptoCurrencyType getCryptoCurrencyType(){ return this.cryptoCurrencyType; }
    public void setCryptoCurrencyType(CryptoCurrencyType cryptoCurrency){ this.cryptoCurrencyType = cryptoCurrency; }

    @Override
    public BusinessTransactionStatus getStatus(){ return this.transactionStatus; }
    public void setStatus(BusinessTransactionStatus status){ this.transactionStatus = status; }

}
