package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantLoadEarningSettingsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantRegisterEarningsSettingsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.EarningsSettingsNotRegisteredException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;


/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.MatchingEngineManager</code>
 * provide all the published methods of the matching engine middleware cbp plug-in.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/01/2016.
 */
public interface MatchingEngineManager extends FermatManager {

    /**
     * Through the method <code>registerEarningsSettings</code> you can register an earnings settings instance with which you can
     * manage the earnings settings of a specific wallet.
     *
     * @param walletReference information of the referenced wallet.
     *
     * @return an instance of the earnings settings object related with the given wallet information.
     *
     * @throws CantRegisterEarningsSettingsException if something goes wrong.
     */
    EarningsSettings registerEarningsSettings(WalletReference walletReference) throws CantRegisterEarningsSettingsException;

    /**
     * Through the method <code>loadEarningsSettings</code> you can get an earnings settings instance with which you can
     * manage the earnings settings of a specific wallet.
     *
     * @param walletPublicKey the public key of the wallet that you want to load.
     *
     * @return an instance of the earnings settings object related with the given wallet information.
     *
     * @throws CantLoadEarningSettingsException       if something goes wrong.
     * @throws EarningsSettingsNotRegisteredException when the earnings settings for the given public key cannot be found.
     */
    EarningsSettings loadEarningsSettings(String walletPublicKey) throws CantLoadEarningSettingsException, EarningsSettingsNotRegisteredException;

    /**
     * @return The Earnings Extractor manager
     */
    EarningExtractorManager getEarningsExtractorManager();

    EarningsSearch getSearch(EarningsPair earningsPair);
}
