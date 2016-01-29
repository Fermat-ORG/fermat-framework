package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_api.layer.world.interfaces.CurrencyPair;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/01/2016.
 */
public interface EarningsPair {

    /**
     * @return the id of the earning pair.
     */
    UUID getId();

    /**
     * @return the currency pair that we're managing.
     */
    CurrencyPair getCurrencyPair();

    /**
     * @return the currency that we've selected to earn in this currency pair.
     */
    Currency getSelectedEarningCurrency();

    /**
     * @return the wallet public key referencing the wallet where the earnings pair belongs.
     */
    String walletPublicKey();



}
