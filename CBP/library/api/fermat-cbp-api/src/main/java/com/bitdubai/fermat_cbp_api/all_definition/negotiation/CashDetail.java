package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CashCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CashOperationType;

/**
 * Created by jorge on 10-10-2015.
 */
public interface CashDetail {
    CashCurrencyType getCurrency();
    CashOperationType getOperation();
    String getOperationInformation();
}
