package com.bitdubai.fermat_cbp_api.all_definition.business_transaction;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */

public interface BusinessTransaction {

    UUID getTransactionId();

    UUID getOperationId();

    String getPublicKeyBroker();

    BusinessTransactionStatus getStatus();

    CurrencyType getMerchandiseCurrency();

    float getMerchandiseAmount();

    ReferenceCurrency getReferenceCurrency();

    float getReferenceCurrencyPrice();


}
