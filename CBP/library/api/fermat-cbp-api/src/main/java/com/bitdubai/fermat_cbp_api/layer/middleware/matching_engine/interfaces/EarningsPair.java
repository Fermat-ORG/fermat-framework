package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair</code>
 * represents an earnings pair object and containts all the methods needed to get their information.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/01/2016.
 */
public interface EarningsPair {

    /**
     * @return the id of the earning pair.
     */
    UUID getId();

    /**
     * @return the currency which we decided to extract the earnings.
     */
    Currency getSelectedCurrency();

    /**
     * @return the currency that we're linking to the previous selected currency to conform the pair.
     */
    Currency getLinkedCurrency();

    /**
     * @return the wallet public key referencing the wallet where the earnings pair belongs.
     */
    String walletPublicKey();

    // TODO add all the information referencing the wallet that we have linked.

}
