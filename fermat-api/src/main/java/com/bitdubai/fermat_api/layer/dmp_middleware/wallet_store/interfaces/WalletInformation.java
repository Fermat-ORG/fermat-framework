package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.WalletInstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.exceptions.CantGetInstallationStatusException;

/**
 * This class let us access to additional internal wallet information
 */
public interface WalletInformation {

    /**
     * This method gives us the installation status of the represented wallet
     *
     * @return an installation status represented by the enum WalletInstallationStatus
     */
    public WalletInstallationStatus getWalletInstallationStatus() throws CantGetInstallationStatusException;
}
