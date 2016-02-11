package com.bitdubai.fermat_cbp_api.all_definition.request;

import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.RequestStatus;

import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.09.15..
 */
public interface Request {

    UUID getRequestId();

    String getRequestSenderPublicKey();

    String getRequestDestinationPublicKey();

    MoneyType getMerchandiseCurrency();

    float getMerchandiseAmount();

    MoneyType getPaymentCurrency();

    RequestStatus getRequestStatus();
}
