package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.InputTransactionState;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction</code>
 * represents an InputTransaction and contains all the public necessary methods to get its information .
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 */
public interface InputTransaction {

    String getId();

    Currency getCurrencyGiving();

    float getAmountGiving();

    Currency getCurrencyReceiving();

    float getAmountReceiving();

    InputTransactionState getState();

}
