package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListInputTransactionsException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction</code>
 * contains all the information about an earning transaction.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 */
public interface EarningTransaction {

    /**
     * @return the id of the earning transaction.
     */
    UUID getId();

    /**
     * @return the currency which we decided to extract the earnings.
     */
    Currency getEarningCurrency();

    /**
     * @return the amount calculated of the earning.
     */
    float getAmount();

    /**
     * @return a list of the input transactions where we define the earning.
     */
    List<InputTransaction> listInputTransactions() throws CantListInputTransactionsException;

    /**
     * @return the state of the earning transaction.
     */
    EarningTransactionState getState();

}
