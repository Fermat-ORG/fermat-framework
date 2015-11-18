package com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by angel on 17/11/15.
 */
public interface CryptoBrokerActorContract {

    UUID getContractId();

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

    Collection<ContractClause> getClauses();
}
