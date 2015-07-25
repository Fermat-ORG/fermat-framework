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
    public InstallationStatus getWalletInstallationStatus(UUID walletId) throws CantGetInstallationStatusException;

    public InstallationStatus getLanguageInstallationStatus(UUID languageId);

    public InstallationStatus getSkinInstallationStatus(UUID skinId);

    public InstallationStatus getTranslatorInstallationStatus(UUID translatorId);

    public InstallationStatus getDesignerInstallationStatus (UUID designerId);

    public void setWalletInstallationStatus(UUID walletId, InstallationStatus status) throws CantGetInstallationStatusException;

    public void setLanguageInstallationStatus(UUID languageId, InstallationStatus status);

    public void setSkinInstallationStatus(UUID skinId, InstallationStatus status);

    public void setTranslatorInstallationStatus(UUID translatorId, InstallationStatus status);

    public void setDesignerInstallationStatus (UUID designerId, InstallationStatus status);
}
