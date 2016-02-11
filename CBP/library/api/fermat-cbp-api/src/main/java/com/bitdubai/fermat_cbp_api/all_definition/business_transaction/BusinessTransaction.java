package com.bitdubai.fermat_cbp_api.all_definition.business_transaction;

import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionStatus;
import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */

public interface BusinessTransaction {

    UUID getTransactionId();

    String getPublicKeyBroker();

    MoneyType getMerchandiseCurrency();

    float getMerchandiseAmount();

    UUID getExecutionTransactionId();

    BusinessTransactionStatus getStatus();

}
