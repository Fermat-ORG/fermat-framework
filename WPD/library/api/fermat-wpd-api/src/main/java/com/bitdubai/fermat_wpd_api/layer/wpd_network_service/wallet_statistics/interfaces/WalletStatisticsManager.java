package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_statistics.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_statistics.exceptions.CantGetWalletStatisticsException;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletStatisticsManager</code>
 * indicates the functionality of a WalletStatisticsManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletStatisticsManager extends FermatManager {

    /**
     * This method gives us the wallet statistics associated to a particular wallet.
     *
     * @param walletCatalogId the id that identifies the wallet in the wallet catalogue
     * @return the statistics associated to that wallet.
     */
    WalletStatistics getWalletStatistics(UUID walletCatalogId) throws CantGetWalletStatisticsException;
}