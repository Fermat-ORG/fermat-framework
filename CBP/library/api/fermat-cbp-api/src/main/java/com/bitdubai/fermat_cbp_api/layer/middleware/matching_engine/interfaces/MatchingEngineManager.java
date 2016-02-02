package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantGetEarningSettingsException;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.MatchingEngineManager</code>
 * provide all the published methods of the matching engine middleware cbp plug-in.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/01/2016.
 */
public interface MatchingEngineManager {

    /**
     * Through the method <code>getEarningsSettings</code> you can get an earnings settings instance with which you can
     * manage the earnings settings of a specific wallet.
     *
     * @param walletPublicKey public key of the referenced wallet.
     *
     * @return an instance of the earnings settings object related with the given wallet information.
     *
     * @throws CantGetEarningSettingsException if something goes wrong.
     */
    EarningsSettings getEarningsSettings(

            String walletPublicKey
            // TODO ADD THE PARAMETERS FOR ALL THE NECESSARY WALLET INFORMATION

    ) throws CantGetEarningSettingsException;

}
