package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;
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
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException
     */
    void persistDigitalAssetInLocalStorage(DigitalAsset digitalAsset)throws CantCreateDigitalAssetFileException;

    /**
     * This method persists the DigitalAssetMetadata XML file in local storage.
     * @param digitalAssetMetadata
     * @param internalId Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException
     */
    void persistDigitalAssetMetadataInLocalStorage(DigitalAssetMetadata digitalAssetMetadata, String internalId)throws CantCreateDigitalAssetFileException ;

    /**
     * This method get the XML file and cast the DigitalAssetMetadata object
     * @param internalId AssetIssuing: Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @return
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException
     */
    DigitalAssetMetadata getDigitalAssetMetadataFromLocalStorage(String internalId) throws CantGetDigitalAssetFromLocalStorageException ;

    /**
     * This method get the XML file and cast the DigitalAsset object
     * @param assetPublicKey
     * @return
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException
     */
    DigitalAsset getDigitalAssetFromLocalStorage(String assetPublicKey) throws CantGetDigitalAssetFromLocalStorageException ;

    /**
     * This method delete a XML file from the local storage
     * @param internalId Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException
     */
    void deleteDigitalAssetMetadataFromLocalStorage(String internalId) throws CantDeleteDigitalAssetFromLocalStorageException ;

    void setDigitalAssetLocalFilePath(String digitalAssetFileStoragePath);

    void setAssetIssuerWalletManager(AssetIssuerWalletManager assetIssuerWalletManager) throws CantSetObjectException;

    void setAssetUserWalletManager(AssetUserWalletManager assetUserWalletManager) throws CantSetObjectException;

    boolean isAssetTransactionHashAvailableBalanceInAssetWallet(String genesisTransactionHash, String assetPublicKey) throws DAPException;

    void setWalletPublicKey(String walletPublicKey) throws CantSetObjectException ;

    void setBitcoinCryptoNetworkManager(BitcoinNetworkManager bitcoinNetworkManager);
}
