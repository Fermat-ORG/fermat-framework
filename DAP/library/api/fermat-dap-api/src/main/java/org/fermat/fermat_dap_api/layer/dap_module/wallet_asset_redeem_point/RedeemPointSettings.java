package org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantGetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantLoadWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultLanguageException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.exceptions.CantSetDefaultSkinException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * RedeemPointSettings
 *
 * @author Francisco Vásquez on 15/09/15.
 * @version 1.0
 */
public class RedeemPointSettings implements WalletSettings, Serializable {

    private UUID languageId;
    private UUID skinId;
    private boolean isPresentationHelpEnabled;
    private boolean isContactsHelpEnabled;
    private List<BlockchainNetworkType> blockchainNetwork;
    private int blockchainNetworkPosition;
    private boolean notificationEnabled;
    private boolean assetNotificationEnabled;

    public int getBlockchainNetworkPosition() {
        return blockchainNetworkPosition;
    }

    public void setBlockchainNetworkPosition(int blockchainNetworkPosition) {
        this.blockchainNetworkPosition = blockchainNetworkPosition;
    }

    public List<BlockchainNetworkType> getBlockchainNetwork() {
        return blockchainNetwork;
    }

    public void setBlockchainNetwork(List<BlockchainNetworkType> blockchainNetwork) {
        this.blockchainNetwork = blockchainNetwork;
    }

    public boolean isContactsHelpEnabled() {
        return isContactsHelpEnabled;
    }

    public boolean isPresentationHelpEnabled() {
        return isPresentationHelpEnabled;
    }

    @Override
    public UUID getDefaultLanguage() throws CantGetDefaultLanguageException, CantLoadWalletSettings {
        return languageId;
    }

    @Override
    public void setDefaultLanguage(UUID languageId) throws CantSetDefaultLanguageException, CantLoadWalletSettings {
        this.languageId = languageId;
    }

    @Override
    public UUID getDefaultSkin() throws CantGetDefaultSkinException, CantLoadWalletSettings {
        return skinId;
    }

    @Override
    public void setDefaultSkin(UUID skinId) throws CantSetDefaultSkinException, CantLoadWalletSettings {
        this.skinId = skinId;
    }

    public void setIsContactsHelpEnabled(boolean isContactsHelpEnabled) {
        this.isContactsHelpEnabled = isContactsHelpEnabled;
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean isPresentationHelpEnabled) {
        this.isPresentationHelpEnabled = isPresentationHelpEnabled;
    }

    public boolean getNotificationEnabled() {
        return this.notificationEnabled;
    }

    public void setNotificationEnabled(boolean notificationEnabled) {
        this.notificationEnabled = notificationEnabled;
    }

    public boolean getAssetNotificationEnabled() {
        return assetNotificationEnabled;
    }

    public void setAssetNotificationEnabled(boolean assetNotificationEnabled) {
        this.assetNotificationEnabled = assetNotificationEnabled;
    }
}
