package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BankCurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BankOperationType;

/**
 * Created by jorge on 10-10-2015.
 */
public interface BankDetail {
    BankCurrencyType getCurrency();
    BankOperationType getOperation();
    String getOperationInformation();
}
