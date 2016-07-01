package org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/10/15.
 */
public abstract class AbstractDigitalAssetVault implements DigitalAssetVault {

    //public String LOCAL_STORAGE_PATH="digital-asset-metadata/";
    protected org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager assetUserWalletManager;
    protected final String digitalAssetFileName = "digitalAsset";
    protected final String digitalAssetMetadataFileName = "digitalAssetMetadata";
    protected String LOCAL_STORAGE_PATH = "digital-asset-swap";
    protected FileLifeSpan FILE_LIFE_SPAN = FileLifeSpan.PERMANENT;
    protected FilePrivacy FILE_PRIVACY = FilePrivacy.PRIVATE;
    protected UUID pluginId;
    protected PluginFileSystem pluginFileSystem;
    protected String digitalAssetFileStoragePath;
    protected org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager assetIssuerWalletManager;
    protected String walletPublicKey = org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities.WALLET_PUBLIC_KEY;
    protected org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager actorAssetIssuerManager;
    protected org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager actorAssetUserManager;
    protected BlockchainManager<ECKey, Transaction> bitcoinNetworkManager;

    /**
     * Set the UUID from this plugin
     *
     * @param pluginId
          */
    public void setPluginId(UUID pluginId) throws CantSetObjectException {
        if (pluginId == null) {
            throw new CantSetObjectException("pluginId is null");
        }
        this.pluginId = pluginId;
    }

    /**
     * Set the PliginFileSystem used to persist Digital Assets in local storage
     *
     * @param pluginFileSystem
     * @throws
     */
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException {
        if (pluginFileSystem == null) {
            throw new CantSetObjectException("pluginFileSystem is null");
        }
        this.pluginFileSystem = pluginFileSystem;
    }

    public void setActorAssetUserManager(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager actorAssetUserManager) {
        this.actorAssetUserManager = actorAssetUserManager;
    }

    public void setActorAssetIssuerManager(org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager actorAssetIssuerManager) {
        this.actorAssetIssuerManager = actorAssetIssuerManager;
    }

    /**
     * This method persists the DigitalAsset XML file in local storage.
     *
     * @param digitalAsset
     * @throws org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException
     */
    public void persistDigitalAssetInLocalStorage(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset digitalAsset) throws org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException {
        try {
            String digitalAssetInnerXML = digitalAsset.toString();
            persistXMLStringInLocalStorage(digitalAssetInnerXML, digitalAssetFileName, digitalAsset.getPublicKey());
        } catch (CantPersistFileException | CantCreateFileException exception) {
            throw new org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException(exception, "Persisting the digital asset objects in local storage", "Cannot create or persist the file");
        }

    }

    /**
     * This method persists the DigitalAssetMetadata XML file in local storage.
     *
     * @param digitalAssetMetadata
     * @param internalId           Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @throws org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException
     */
    public synchronized void persistDigitalAssetMetadataInLocalStorage(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata digitalAssetMetadata, String internalId) throws org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException {
        org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset digitalAsset = digitalAssetMetadata.getDigitalAsset();
        try {
            String digitalAssetInnerXML = digitalAsset.toString();
            persistXMLStringInLocalStorage(digitalAssetInnerXML, digitalAssetFileName, internalId);
            String digitalAssetMetadataInnerXML = digitalAssetMetadata.toString();
            persistXMLStringInLocalStorage(digitalAssetMetadataInnerXML, digitalAssetMetadataFileName, internalId);
        } catch (CantPersistFileException | CantCreateFileException exception) {
            throw new org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException(exception, "Persisting the digital asset objects in local storage", "Cannot create or persist the file");
        }

    }

    private void persistXMLStringInLocalStorage(String innerXML, String directory, String fileName) throws CantCreateFileException, CantPersistFileException {
        PluginTextFile digitalAssetFile = this.pluginFileSystem.createTextFile(this.pluginId, directory, fileName, this.FILE_PRIVACY, this.FILE_LIFE_SPAN);
        digitalAssetFile.setContent(innerXML);
        digitalAssetFile.persistToMedia();
    }

    /**
     * This method get the XML file and cast the DigitalAssetMetadata object
     *
     * @param internalId AssetIssuing: Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @return
     * @throws org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException
     */
    public org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata getDigitalAssetMetadataFromLocalStorage(String internalId) throws org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException {
        try {
            PluginTextFile digitalAssetMetadataFile = this.pluginFileSystem.getTextFile(this.pluginId, digitalAssetMetadataFileName, internalId, FILE_PRIVACY, FILE_LIFE_SPAN);
            String digitalAssetMetadataXMLString = digitalAssetMetadataFile.getContent();
            return (org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata) XMLParser.parseXML(digitalAssetMetadataXMLString, new org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata());
        } catch (FileNotFoundException exception) {
            throw new org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot find " + internalId + "' file");
        } catch (CantCreateFileException exception) {
            throw new org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot create " + internalId + "' file");
        } catch (Exception exception) {
            throw new org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Unexpected exception getting " + internalId + "' file");
        }
    }

    public org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset getDigitalAssetFromLocalStorage(String assetPk) throws org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException {
        try {
            PluginTextFile digitalAssetMetadataFile = this.pluginFileSystem.getTextFile(this.pluginId, digitalAssetFileName, assetPk, FILE_PRIVACY, FILE_LIFE_SPAN);
            String digitalAssetMetadataXMLString = digitalAssetMetadataFile.getContent();
            return (org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset) XMLParser.parseXML(digitalAssetMetadataXMLString, new org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset());
        } catch (FileNotFoundException exception) {
            throw new org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot find " + assetPk + "' file");
        } catch (CantCreateFileException exception) {
            throw new org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot create " + assetPk + "' file");
        } catch (Exception exception) {
            throw new org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Unexpected exception getting " + assetPk + "' file");
        }
    }

    /**
     * This method delete a XML file from the local storage
     *
     * @param internalId Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @throws CantDeleteDigitalAssetFromLocalStorageException
     */
    public void deleteDigitalAssetMetadataFromLocalStorage(String internalId) throws CantDeleteDigitalAssetFromLocalStorageException {
        try {
            PluginTextFile digitalAssetMetadataFile = this.pluginFileSystem.getTextFile(this.pluginId, digitalAssetMetadataFileName, internalId, FILE_PRIVACY, FILE_LIFE_SPAN);
            digitalAssetMetadataFile.delete();
        } catch (FileNotFoundException exception) {
            throw new CantDeleteDigitalAssetFromLocalStorageException(exception, "Deleting Digital Asset file from local storage", "Cannot find " + this.LOCAL_STORAGE_PATH + digitalAssetMetadataFileName + "' file");
        } catch (CantCreateFileException exception) {
            throw new CantDeleteDigitalAssetFromLocalStorageException(exception, "Deleting Digital Asset file from local storage", "Cannot create " + this.LOCAL_STORAGE_PATH + digitalAssetMetadataFileName + "' file");
        } catch (Exception exception) {
            throw new CantDeleteDigitalAssetFromLocalStorageException(exception, "Deleting Digital Asset file from local storage", "Unexpected exception getting '" + this.LOCAL_STORAGE_PATH + digitalAssetMetadataFileName + "' file");
        }
    }

    public void setDigitalAssetLocalFilePath(String digitalAssetFileStoragePath) {
        this.digitalAssetFileStoragePath = digitalAssetFileStoragePath;
    }

    public void setAssetIssuerWalletManager(org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager assetIssuerWalletManager) throws CantSetObjectException {
        if (assetIssuerWalletManager == null) {
            throw new CantSetObjectException("assetIssuerWalletManager is null");
        }
        this.assetIssuerWalletManager = assetIssuerWalletManager;
    }

    public void setAssetUserWalletManager(org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager assetUserWalletManager) throws CantSetObjectException {
        if (assetUserWalletManager == null) {
            throw new CantSetObjectException("assetUserWalletManager is null");
        }
        this.assetUserWalletManager = assetUserWalletManager;
    }

    public boolean isAssetTransactionHashAvailableBalanceInAssetWallet(String genesisTransactionHash, String assetPublicKey, BlockchainNetworkType networkType) throws org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {
        try {
            org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet assetIssuerWallet = this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey, networkType);
            List<org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction> assetIssuerWalletTransactionList = assetIssuerWallet.getTransactionsAll(
                    org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType.AVAILABLE,
                    org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType.CREDIT,
                    assetPublicKey);
            for (org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction assetIssuerWalletTransaction : assetIssuerWalletTransactionList) {
                String transactionHashFromIssuerWallet = assetIssuerWalletTransaction.getTransactionHash();
                if (genesisTransactionHash.equals(transactionHashFromIssuerWallet)) {
                    return true;
                }
            }
            return false;
        } catch (org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException exception) {
            throw new org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException(exception, "Checking the available balance in Asset issuer wallet", "Cannot get the transactions from Asset Issuer wallet");
        } catch (org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException exception) {
            throw new org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException(exception, "Checking the available balance in Asset issuer wallet", "Cannot load the Asset Issuer wallet");
        }

    }

    public void setWalletPublicKey(String walletPublicKey) throws CantSetObjectException {
        if (walletPublicKey == null) {
            throw new CantSetObjectException("walletPublicKey is null");
        }
        System.out.println("The wallet public key in vault is " + this.walletPublicKey);
    }

    public void setBitcoinCryptoNetworkManager(BlockchainManager<ECKey, Transaction> bitcoinNetworkManager) {
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    public org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata updateMetadataTransactionChain(String genesisTx, CryptoTransaction cryptoTransaction) throws org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException, org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException {
        return updateMetadataTransactionChain(genesisTx, cryptoTransaction.getTransactionHash(), cryptoTransaction.getBlockHash(), cryptoTransaction.getBlockchainNetworkType());
    }


    public org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata updateMetadataTransactionChain(String genesisTx, String txHash, String blockHash, BlockchainNetworkType blockchainNetworkType) throws org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException, org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException {
        org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata digitalAssetMetadata = getDigitalAssetMetadataFromLocalStorage(genesisTx);
        digitalAssetMetadata.addNewTransaction(txHash, blockHash);
        persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, genesisTx);
        return digitalAssetMetadata;
    }

    public void updateWalletBalance(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata digitalAssetMetadata,
                                    CryptoTransaction genesisTransaction,
                                    org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType,
                                    org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType transactionType,
                                    org.fermat.fermat_dap_api.layer.all_definition.enums.DAPTransactionType dapTransactionType,
                                    String externalActorPublicKey,
                                    Actors externalActorType,
                                    String memo) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException, org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException, CantRegisterCreditException, org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException, org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException, org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException, CantGetAssetUserActorsException {
        String mySelf;
        BlockchainNetworkType networkType = genesisTransaction.getBlockchainNetworkType();
        switch (dapTransactionType) {
            case DISTRIBUTION: {
                org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet issuerWallet = this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey, networkType);
                AssetIssuerWalletBalance assetIssuerWalletBalance = issuerWallet.getBalance();
                mySelf = this.actorAssetIssuerManager.getActorAssetIssuer().getActorPublicKey();
                Actors mySelfType = Actors.DAP_ASSET_ISSUER;
                if (Validate.isObjectNull(externalActorPublicKey)) {
                    externalActorPublicKey = mySelf;
                    externalActorType = mySelfType;
                }
                switch (transactionType) {
                    case CREDIT: {
                        org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper = new org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper(
                                digitalAssetMetadata,
                                genesisTransaction,
                                externalActorPublicKey,
                                externalActorType,
                                mySelf,
                                mySelfType,
                                memo
                        );
                        assetIssuerWalletBalance.credit(assetIssuerWalletTransactionRecordWrapper, balanceType);
                        break;
                    }
                    case DEBIT: {
                        org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper = new org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper(
                                digitalAssetMetadata,
                                genesisTransaction,
                                mySelf,
                                mySelfType,
                                externalActorPublicKey,
                                externalActorType,
                                memo
                        );
                        assetIssuerWalletBalance.debit(assetIssuerWalletTransactionRecordWrapper, balanceType);
                        break;
                    }
                }
                break;
            }
            case RECEPTION: {
                org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet userWallet = this.assetUserWalletManager.loadAssetUserWallet(this.walletPublicKey, networkType);
                org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance assetUserWalletBalance = userWallet.getBalance();
                mySelf = this.actorAssetUserManager.getActorAssetUser().getActorPublicKey();
                Actors mySelfType = Actors.DAP_ASSET_USER;
                if (Validate.isObjectNull(externalActorPublicKey)) {
                    externalActorPublicKey = mySelf;
                    externalActorType = mySelfType;
                }
                switch (transactionType) {
                    case DEBIT: {
                        org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper assetUserWalletTransactionRecordWrapper = new org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper(
                                digitalAssetMetadata,
                                genesisTransaction,
                                mySelf,
                                mySelfType,
                                externalActorPublicKey,
                                externalActorType,
                                memo
                        );
                        assetUserWalletBalance.debit(assetUserWalletTransactionRecordWrapper, balanceType);
                        break;
                    }
                    case CREDIT:
                        org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper assetUserWalletTransactionRecordWrapper = new org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper(
                                digitalAssetMetadata,
                                genesisTransaction,
                                externalActorPublicKey,
                                externalActorType,
                                mySelf,
                                mySelfType,
                                memo
                        );
                        assetUserWalletBalance.credit(assetUserWalletTransactionRecordWrapper, balanceType);
                        break;
                }
                break;
            }
        }
    }
}
