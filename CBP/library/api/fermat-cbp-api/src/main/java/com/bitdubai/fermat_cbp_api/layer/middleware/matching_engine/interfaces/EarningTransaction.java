package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantMarkEarningTransactionAsExtractedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.EarningTransactionNotFoundException;

import java.io.Serializable;
import java.util.UUID;


/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction</code>
 * contains all the information about an earning transaction.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 */
public interface EarningTransaction extends Serializable {

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
     * Change the Earning Transaction state from {@link EarningTransactionState#CALCULATED} to {@link EarningTransactionState#EXTRACTED}
     *
     * @throws EarningTransactionNotFoundException
     * @throws CantMarkEarningTransactionAsExtractedException
     */
    void markAsExtracted() throws EarningTransactionNotFoundException, CantMarkEarningTransactionAsExtractedException;

    /**
     * @return the state of the earning transaction.
     */
    EarningTransactionState getState();

    /**
     * @return the time when the earning transaction was computed.
     */
    long getTime();

    int getDay();

    int getDayOfYear();

    int getWeekOfYear();

    int getMonth();

    int getYear();

}
