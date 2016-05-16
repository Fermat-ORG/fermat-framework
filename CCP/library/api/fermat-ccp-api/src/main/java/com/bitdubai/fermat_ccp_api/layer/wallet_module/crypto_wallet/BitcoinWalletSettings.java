package com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public class BitcoinWalletSettings implements WalletSettings,Serializable {

    private IntraUserLoginIdentity lastSelectedIdentity;
    private boolean isPresentationHelpEnabled;
    private boolean isContactsHelpEnabled;
    private Map<Long, Long>  runningDailyBalance ;
    private BlockchainNetworkType blockchainNetworkType;
    private boolean notificationEnabled;
    private boolean isBlockchainDownloadEnabled;

    public BitcoinWalletSettings() {
        this.lastSelectedIdentity = null;
    }

    public IntraUserLoginIdentity getLastSelectedIdentity() {
        return lastSelectedIdentity;
    }

    public void setLastSelectedIdentity(IntraUserLoginIdentity lastSelectedIdentity) {
        this.lastSelectedIdentity = lastSelectedIdentity;
    }

    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException {
        return null;
    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException {
        return null;
    }

    public Map<Long, Long> getRunningDailyBalance()  {
        return this.runningDailyBalance ;
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException {

    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException {

    }

    public boolean isPresentationHelpEnabled() {
        return isPresentationHelpEnabled;
    }


    public boolean isBlockchainDownloadEnabled() {
        return isBlockchainDownloadEnabled;
    }

    public void setIsPresentationHelpEnabled(boolean isPresentationHelpEnabled) {
        this.isPresentationHelpEnabled = isPresentationHelpEnabled;
    }

    public void setIsBlockchainDownloadEnabled(boolean isBlockchainDownloadEnabled) {
        this.isBlockchainDownloadEnabled = isBlockchainDownloadEnabled;
    }

    public boolean isContactsHelpEnabled() {
        return isContactsHelpEnabled;
    }

    public void setIsContactsHelpEnabled(boolean isContactsHelpEnabled) {
        this.isContactsHelpEnabled = isContactsHelpEnabled;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }

    public void setRunningDailyBalance(Map<Long, Long>  runningDailyBalance) {
        this.runningDailyBalance = runningDailyBalance;


    }

    public boolean getNotificationEnabled() {
        return this.notificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }
}
