package com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Franklin Marcano on 21/01/16.
 */
public class CryptoCustomerWalletPreferenceSettings implements WalletSettings, Serializable {

    private boolean isHomeTutorialDialogEnabled;
    private CryptoCustomerWalletAssociatedSetting bitcoinWallet;
    private List<CryptoCustomerWalletProviderSetting> providers;
    private boolean isWalletConfigured;
    private boolean isWizardStartActivity = true;
    private BitcoinFee bitcoinFee = BitcoinFee.NORMAL;
    private FeeOrigin feeOrigin = FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT;
    private BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

    public boolean isHomeTutorialDialogEnabled() {
        return isHomeTutorialDialogEnabled;
    }

    public void setIsHomeTutorialDialogEnabled(boolean isHomeTutorialDialogEnabled) {
        this.isHomeTutorialDialogEnabled = isHomeTutorialDialogEnabled;
    }

    public boolean isWalletConfigured() {
        return isWalletConfigured;
    }

    public void setIsWalletConfigured(boolean isWalletConfigured) {
        this.isWalletConfigured = isWalletConfigured;
    }


    /**
     * This method let us know the default language of a wallet
     *
     * @return the identifier of the default language of the wallet
     * @throws CantGetDefaultLanguageException
     */
    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException {
        return null;
    }

    /**
     * This method let us know the default skin of a wallet
     *
     * @return the identifier of the default skin of the wallet
     * @throws CantGetDefaultSkinException
     */
    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException {
        return null;
    }

    /**
     * This method let us set the default language for a wallet
     *
     * @param languageId the identifier of the language to set as default
     * @throws CantSetDefaultLanguageException
     */
    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException {

    }

    /**
     * This method let us set the default skin for a wallet
     *
     * @param skinId the identifier of the skin to set as default
     * @throws CantSetDefaultSkinException
     */
    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException {

    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {
        isHomeTutorialDialogEnabled = b;
    }

    public List<CryptoCustomerWalletProviderSetting> getSelectedProviders() {
        if (providers == null)
            providers = new ArrayList<>();
        return providers;
    }

    public CryptoCustomerWalletAssociatedSetting getSelectedBitcoinWallet() {
        return bitcoinWallet;
    }

    public void setSelectedBitcoinWallet(CryptoCustomerWalletAssociatedSetting walletSetting) {
        this.bitcoinWallet = walletSetting;
    }

    public boolean isWizardStartActivity() {
        return isWizardStartActivity;
    }

    public void setIsWizardStartActivity(boolean isWizardStartActivity) {
        this.isWizardStartActivity = isWizardStartActivity;
    }

    public BitcoinFee getBitcoinFee() {
        return bitcoinFee;
    }

    public void setBitcoinFee(BitcoinFee bitcoinFee) {
        this.bitcoinFee = bitcoinFee;
    }

    public FeeOrigin getFeeOrigin() {
        return feeOrigin;
    }

    public void setFeeOrigin(FeeOrigin feeOrigin) {
        this.feeOrigin = feeOrigin;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {
        return blockchainNetworkType;
    }

    public void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType) {
        this.blockchainNetworkType = blockchainNetworkType;
    }
}
