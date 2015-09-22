package com.bitdubai.fermat_cbp_api.all_definition.contract;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;

import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */

public interface Contract {

    UUID getContractId();

    String getPublicKeyCustomer();

    String getPublicKeyBroker();

    ContractStatus getStatus();

    float getMerchandiseAmount();

    CurrencyType getMerchandiseCurrency();

    float getReferencePrice();

    ReferenceCurrency getReferenceCurrency();

    float getPaymentAmount();

    CurrencyType getPaymentCurrency();

    long getPaymentExpirationDate();

    long getMerchandiseDeliveryExpirationDate();

}

