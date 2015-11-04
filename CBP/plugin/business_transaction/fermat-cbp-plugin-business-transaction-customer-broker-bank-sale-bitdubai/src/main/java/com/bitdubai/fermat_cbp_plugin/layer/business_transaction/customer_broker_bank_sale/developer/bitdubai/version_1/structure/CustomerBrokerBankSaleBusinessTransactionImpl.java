package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_broker_bank_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.customer_broke_bank_sale.interfaces.CustomerBrokerBankSale;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public class CustomerBrokerBankSaleBusinessTransactionImpl implements BusinessTransaction, CustomerBrokerBankSale {

    private final UUID transactionId;
    private final UUID contractId;
    private final String publicKeyBroker;
    private final String publicKeyCustomer;
    private final UUID paymentTransactionId;
    private final CurrencyType paymentCurrency;
    private final CurrencyType merchandiseCurrency;
    private final float merchandiseAmount;
    private final UUID executionTransactionId;
    private final BankCurrencyType bankCurrencyType;
    private final BankOperationType bankOperationType;
    private final BusinessTransactionStatus transactionStatus;

    public CustomerBrokerBankSaleBusinessTransactionImpl(
            UUID transactionId,
            UUID contractId,
            String publicKeyBroker,
            String publicKeyCustomer,
            UUID paymentTransactionId,
            CurrencyType paymentCurrency,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            UUID executionTransactionId,
            BankCurrencyType bankCurrencyType,
            BankOperationType bankOperationType,
            BusinessTransactionStatus transactionStatus
    ){
        this.transactionId = transactionId;
        this.contractId = contractId;
        this.publicKeyBroker = publicKeyBroker;
        this.publicKeyCustomer = publicKeyCustomer;
        this.paymentTransactionId = paymentTransactionId;
        this.paymentCurrency = paymentCurrency;
        this.merchandiseCurrency = merchandiseCurrency;
        this.merchandiseAmount = merchandiseAmount;
        this.executionTransactionId = executionTransactionId;
        this.bankCurrencyType = bankCurrencyType;
        this.bankOperationType = bankOperationType;
        this.transactionStatus = transactionStatus;
    }

    @Override
    public UUID getTransactionId(){ return this.transactionId; }

    @Override
    public UUID getContractId(){ return this.contractId; }

    @Override
    public String getPublicKeyBroker(){ return this.publicKeyBroker; }

    @Override
    public String getPublicKeyCustomer(){ return this.publicKeyCustomer; }

    @Override
    public UUID getPaymentTransactionId(){ return this.paymentTransactionId; }

    @Override
    public CurrencyType getPaymentCurrency(){ return this.paymentCurrency; }

    @Override
    public CurrencyType getMerchandiseCurrency(){ return this.merchandiseCurrency; }

    @Override
    public float getMerchandiseAmount(){ return this.merchandiseAmount; }

    @Override
    public UUID getExecutionTransactionId(){ return this.executionTransactionId; }

    @Override
    public BankCurrencyType getBankCurrencyType(){ return this.bankCurrencyType; }

    @Override
    public BankOperationType getBankOperationType(){ return this.bankOperationType; }

    @Override
    public BusinessTransactionStatus getStatus(){ return this.transactionStatus; }

}
