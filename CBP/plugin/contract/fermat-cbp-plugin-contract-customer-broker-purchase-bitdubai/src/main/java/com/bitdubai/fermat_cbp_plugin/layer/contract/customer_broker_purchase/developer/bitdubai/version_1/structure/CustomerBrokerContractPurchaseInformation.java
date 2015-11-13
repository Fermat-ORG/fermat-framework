package com.bitdubai.fermat_cbp_plugin.layer.contract.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.contract.Contract;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.cbp_contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;

import java.util.UUID;

/**
 * Created by angel on 02/11/15.
 */
public class CustomerBrokerContractPurchaseInformation implements CustomerBrokerContractPurchase {

    private UUID contractId;
    private String publicKeyCustomer;
    private String publicKeyBroker;
    private CurrencyType paymentCurrency;
    private CurrencyType merchandiseCurrency;
    private float referencePrice;
    private ReferenceCurrency referenceCurrency;
    private float paymentAmount;
    private float merchandiseAmount;
    private long paymentExpirationDate;
    private long merchandiseDeliveryExpirationDate;
    private ContractStatus status;

    public CustomerBrokerContractPurchaseInformation(
            UUID contractId,
            String publicKeyCustomer,
            String publicKeyBroker,
            CurrencyType paymentCurrency,
            CurrencyType merchandiseCurrency,
            float referencePrice,
            ReferenceCurrency referenceCurrency,
            float paymentAmount,
            float merchandiseAmount,
            long paymentExpirationDate,
            long merchandiseDeliveryExpirationDate,
            ContractStatus status
    ){
        this.contractId = contractId;
        this.publicKeyCustomer = publicKeyCustomer;
        this.publicKeyBroker = publicKeyBroker;
        this.paymentCurrency = paymentCurrency;
        this.merchandiseCurrency = merchandiseCurrency;
        this.referencePrice = referencePrice;
        this.referenceCurrency = referenceCurrency;
        this.paymentAmount = paymentAmount;
        this.merchandiseAmount = merchandiseAmount;
        this.paymentExpirationDate = paymentExpirationDate;
        this.merchandiseDeliveryExpirationDate = merchandiseDeliveryExpirationDate;
        this.status = status;
    }

    @Override
    public UUID getContractId() {
        return this.contractId;
    }

    @Override
    public String getPublicKeyCustomer() {
        return this.publicKeyCustomer;
    }

    @Override
    public String getPublicKeyBroker() {
        return this.publicKeyBroker;
    }

    @Override
    public CurrencyType getPaymentCurrency() {
        return this.paymentCurrency;
    }

    @Override
    public CurrencyType getMerchandiseCurrency() {
        return this.merchandiseCurrency;
    }

    @Override
    public float getReferencePrice() {
        return this.referencePrice;
    }

    @Override
    public ReferenceCurrency getReferenceCurrency() {
        return this.referenceCurrency;
    }

    @Override
    public float getPaymentAmount() {
        return this.paymentAmount;
    }

    @Override
    public float getMerchandiseAmount() {
        return this.merchandiseAmount;
    }

    @Override
    public long getPaymentExpirationDate() {
        return this.paymentExpirationDate;
    }

    @Override
    public long getMerchandiseDeliveryExpirationDate() {
        return this.merchandiseDeliveryExpirationDate;
    }

    @Override
    public ContractStatus getStatus() {
        return this.status;
    }
}
