package com.bitdubai.fermat_cbp_api.all_definition.contract;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */

public interface Contract {

    String getContractId();

    String getPublicKeyCustomer();
    String getPublicKeyBroker();

    CurrencyType getPaymentCurrency();
    CurrencyType getMerchandiseCurrency();

    float getReferencePrice();
    ReferenceCurrency getReferenceCurrency();

    float getPaymentAmount();
    float getMerchandiseAmount();

    long getPaymentExpirationDate();
    long getMerchandiseDeliveryExpirationDate();

    ContractStatus getStatus();

}

