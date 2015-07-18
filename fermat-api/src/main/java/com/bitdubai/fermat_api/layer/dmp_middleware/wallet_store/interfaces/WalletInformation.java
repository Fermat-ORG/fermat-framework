package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.exceptions.CantGetInstallationStatusException;

import java.util.List;
import java.util.UUID;

/**
 * This class let us access to additional internal wallet information
 */
public interface WalletInformation {

    /**
     * This method gives us the installation status of the represented wallet
     *
     * @return an installation status represented by the enum InstallationStatus
     */
    public InstallationStatus getWalletInstallationStatus() throws CantGetInstallationStatusException;

    public List<UUID>

    public List<UUID> getSkinsInstalled();
}
