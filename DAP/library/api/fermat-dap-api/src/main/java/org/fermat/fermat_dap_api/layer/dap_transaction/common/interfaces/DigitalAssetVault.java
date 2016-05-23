package org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;

import java.util.UUID;


/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 22/10/15.
 */
public interface DigitalAssetVault {

    /**
     * Set the UUID from this plugin
     * @param pluginId
     * @throws CantSetObjectException
     */
    void setPluginId(UUID pluginId) throws CantSetObjectException;

    /**
     * Set the PliginFileSystem used to persist Digital Assets in local storage
     * @param pluginFileSystem
     * @throws CantSetObjectException
     */
    void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException;

    /**
     * This method persists the DigitalAsset XML file in local storage.
     * @param digitalAsset
     * @throws CantCreateDigitalAssetFileException
     */
    void persistDigitalAssetInLocalStorage(DigitalAsset digitalAsset)throws CantCreateDigitalAssetFileException;

    /**
     * This method persists the DigitalAssetMetadata XML file in local storage.
     * @param digitalAssetMetadata
     * @param internalId Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @throws CantCreateDigitalAssetFileException
     */
    void persistDigitalAssetMetadataInLocalStorage(DigitalAssetMetadata digitalAssetMetadata, String internalId)throws CantCreateDigitalAssetFileException ;

    /**
     * This method get the XML file and cast the DigitalAssetMetadata object
     * @param internalId AssetIssuing: Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @return
     * @throws CantGetDigitalAssetFromLocalStorageException
     */
    DigitalAssetMetadata getDigitalAssetMetadataFromLocalStorage(String internalId) throws CantGetDigitalAssetFromLocalStorageException ;

    /**
     * This method delete a XML file from the local storage
     * @param internalId Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @throws CantDeleteDigitalAssetFromLocalStorageException
     */
    void deleteDigitalAssetMetadataFromLocalStorage(String internalId) throws CantDeleteDigitalAssetFromLocalStorageException ;

    void setDigitalAssetLocalFilePath(String digitalAssetFileStoragePath);

    void setAssetIssuerWalletManager(org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager assetIssuerWalletManager) throws CantSetObjectException;

    void setAssetUserWalletManager(AssetUserWalletManager assetUserWalletManager) throws CantSetObjectException;

    boolean isAssetTransactionHashAvailableBalanceInAssetWallet(String genesisTransactionHash, String assetPublicKey, BlockchainNetworkType networkType) throws DAPException;

    void setWalletPublicKey(String walletPublicKey) throws CantSetObjectException ;

    void setBitcoinCryptoNetworkManager(BitcoinNetworkManager bitcoinNetworkManager);
}
