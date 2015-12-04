package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/10/15.
 */
public abstract class AbstractDigitalAssetVault implements DigitalAssetVault {

    //public String LOCAL_STORAGE_PATH="digital-asset-metadata/";
    public AssetUserWalletManager assetUserWalletManager;
    public final String digitalAssetFileName = "digital-asset";
    public final String digitalAssetMetadataFileName = "digital-asset-metadata";
    public String LOCAL_STORAGE_PATH = "digital-asset-swap";
    public FileLifeSpan FILE_LIFE_SPAN = FileLifeSpan.PERMANENT;
    public FilePrivacy FILE_PRIVACY = FilePrivacy.PRIVATE;
    public UUID pluginId;
    public PluginFileSystem pluginFileSystem;
    public String digitalAssetFileStoragePath;
    public AssetIssuerWalletManager assetIssuerWalletManager;
    public String walletPublicKey = "walletPublicKeyTest";
    public ActorAssetIssuerManager actorAssetIssuerManager;
    public ActorAssetUserManager actorAssetUserManager;
    BitcoinNetworkManager bitcoinNetworkManager;

    /**
     * Set the UUID from this plugin
     *
     * @param pluginId
     * @throws CantSetObjectException
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
     * @throws CantSetObjectException
     */
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException {
        if (pluginFileSystem == null) {
            throw new CantSetObjectException("pluginFileSystem is null");
        }
        this.pluginFileSystem = pluginFileSystem;
    }

    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        if (actorAssetUserManager == null) {
            throw new CantSetObjectException("actorAssetUserManager is null");
        }
        this.actorAssetUserManager = actorAssetUserManager;
    }

    public void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) throws CantSetObjectException {
        if (actorAssetIssuerManager == null) {
            throw new CantSetObjectException("actorAssetIssuerManager is null");
        }
        this.actorAssetIssuerManager = actorAssetIssuerManager;
    }

    /**
     * This method persists the DigitalAsset XML file in local storage.
     *
     * @param digitalAsset
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException
     */
    public void persistDigitalAssetInLocalStorage(DigitalAsset digitalAsset) throws CantCreateDigitalAssetFileException {
        try {
            String digitalAssetInnerXML = digitalAsset.toString();
            persistXMLStringInLocalStorage(digitalAssetInnerXML, "digitalAsset", digitalAsset.getPublicKey());
        } catch (CantPersistFileException | CantCreateFileException exception) {
            throw new CantCreateDigitalAssetFileException(exception, "Persisting the digital asset objects in local storage", "Cannot create or persist the file");
        }

    }

    /**
     * This method persists the DigitalAssetMetadata XML file in local storage.
     *
     * @param digitalAssetMetadata
     * @param internalId           Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException
     */
    public void persistDigitalAssetMetadataInLocalStorage(DigitalAssetMetadata digitalAssetMetadata, String internalId) throws CantCreateDigitalAssetFileException {
        DigitalAsset digitalAsset = digitalAssetMetadata.getDigitalAsset();
        //String genesisTransaction=digitalAssetMetadata.getGenesisTransaction();
        try {
            String digitalAssetInnerXML = digitalAsset.toString();
            persistXMLStringInLocalStorage(digitalAssetInnerXML, "digitalAsset", internalId);
            String digitalAssetMetadataInnerXML = digitalAssetMetadata.toString();
            persistXMLStringInLocalStorage(digitalAssetMetadataInnerXML, "digitalAssetMetadata", internalId);
        } catch (CantPersistFileException | CantCreateFileException exception) {
            throw new CantCreateDigitalAssetFileException(exception, "Persisting the digital asset objects in local storage", "Cannot create or persist the file");
        }

    }

    private void persistXMLStringInLocalStorage(String innerXML, String directory,  String fileName) throws CantCreateFileException, CantPersistFileException {
        PluginTextFile digitalAssetFile = this.pluginFileSystem.createTextFile(this.pluginId, directory, fileName, this.FILE_PRIVACY, this.FILE_LIFE_SPAN);
        digitalAssetFile.setContent(innerXML);
        digitalAssetFile.persistToMedia();
    }

    /**
     * This method get the XML file and cast the DigitalAssetMetadata object
     *
     * @param internalId AssetIssuing: Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @return
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException
     */
    public DigitalAssetMetadata getDigitalAssetMetadataFromLocalStorage(String internalId) throws CantGetDigitalAssetFromLocalStorageException {
        try {
            DigitalAssetMetadata digitalAssetMetadataObtainedFromFileStorage = new DigitalAssetMetadata();
            PluginTextFile digitalAssetMetadataFile = this.pluginFileSystem.getTextFile(this.pluginId, "digitalAssetMetadata", internalId, FILE_PRIVACY, FILE_LIFE_SPAN);
            String digitalAssetMetadataXMLString = digitalAssetMetadataFile.getContent();
            digitalAssetMetadataObtainedFromFileStorage = (DigitalAssetMetadata) XMLParser.parseXML(digitalAssetMetadataXMLString, digitalAssetMetadataObtainedFromFileStorage);
            return digitalAssetMetadataObtainedFromFileStorage;
        } catch (FileNotFoundException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot find " + internalId + "' file");
        } catch (CantCreateFileException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot create " + internalId + "' file");
        } catch (Exception exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Unexpected exception getting " +  internalId + "' file");
        }

    }

    /**
     * This method get the XML file and cast the DigitalAsset object
     *
     * @param assetPublicKey
     * @return
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException
     */
    public DigitalAsset getDigitalAssetFromLocalStorage(String assetPublicKey) throws CantGetDigitalAssetFromLocalStorageException {
        try {
            DigitalAsset digitalAssetObtainedFromFileStorage = new DigitalAsset();
            PluginTextFile digitalAssetFile = this.pluginFileSystem.getTextFile(this.pluginId, "digitalAsset", assetPublicKey, FILE_PRIVACY, FILE_LIFE_SPAN);
            String digitalAssetXMLString = digitalAssetFile.getContent();
            digitalAssetObtainedFromFileStorage = (DigitalAsset) XMLParser.parseXML(digitalAssetXMLString, digitalAssetObtainedFromFileStorage);
            return digitalAssetObtainedFromFileStorage;
        } catch (FileNotFoundException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot find " + assetPublicKey + "' file");
        } catch (CantCreateFileException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot create " +assetPublicKey + "' file");
        } catch (Exception exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Unexpected exception getting '" + assetPublicKey + "' file");
        }

    }

    /**
     * This method delete a XML file from the local storage
     *
     * @param internalId Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException
     */
    public void deleteDigitalAssetMetadataFromLocalStorage(String internalId) throws CantDeleteDigitalAssetFromLocalStorageException {
//        String digitalAssetMetadataFileName = "no-file-name-assigned";
//        try {
//            digitalAssetMetadataFileName = internalId + ".xml";
//            PluginTextFile digitalAssetMetadataFile = this.pluginFileSystem.getTextFile(this.pluginId, this.LOCAL_STORAGE_PATH + "digital-asset-metadata", digitalAssetMetadataFileName, FILE_PRIVACY, FILE_LIFE_SPAN);
//            digitalAssetMetadataFile.delete();
//        } catch (FileNotFoundException exception) {
//            throw new CantDeleteDigitalAssetFromLocalStorageException(exception, "Deleting Digital Asset file from local storage", "Cannot find " + this.LOCAL_STORAGE_PATH + digitalAssetMetadataFileName + "' file");
//        } catch (CantCreateFileException exception) {
//            throw new CantDeleteDigitalAssetFromLocalStorageException(exception, "Deleting Digital Asset file from local storage", "Cannot create " + this.LOCAL_STORAGE_PATH + digitalAssetMetadataFileName + "' file");
//        } catch (Exception exception) {
//            throw new CantDeleteDigitalAssetFromLocalStorageException(exception, "Deleting Digital Asset file from local storage", "Unexpected exception getting '" + this.LOCAL_STORAGE_PATH + digitalAssetMetadataFileName + "' file");
//        }
    }

    public void setDigitalAssetLocalFilePath(String digitalAssetFileStoragePath) {
        this.digitalAssetFileStoragePath = digitalAssetFileStoragePath;
    }

    public void setAssetIssuerWalletManager(AssetIssuerWalletManager assetIssuerWalletManager) throws CantSetObjectException {
        if (assetIssuerWalletManager == null) {
            throw new CantSetObjectException("assetIssuerWalletManager is null");
        }
        this.assetIssuerWalletManager = assetIssuerWalletManager;
    }

    public void setAssetUserWalletManager(AssetUserWalletManager assetUserWalletManager) throws CantSetObjectException {
        if (assetUserWalletManager == null) {
            throw new CantSetObjectException("assetUserWalletManager is null");
        }
        this.assetUserWalletManager = assetUserWalletManager;
    }

    public boolean isAssetTransactionHashAvailableBalanceInAssetWallet(String genesisTransactionHash, String assetPublicKey) throws DAPException {
        try {
            AssetIssuerWallet assetIssuerWallet = this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey);
            List<AssetIssuerWalletTransaction> assetIssuerWalletTransactionList = assetIssuerWallet.getTransactionsAll(
                    BalanceType.AVAILABLE,
                    TransactionType.CREDIT,
                    assetPublicKey);
            for (AssetIssuerWalletTransaction assetIssuerWalletTransaction : assetIssuerWalletTransactionList) {
                String transactionHashFromIssuerWallet = assetIssuerWalletTransaction.getTransactionHash();
                if (genesisTransactionHash.equals(transactionHashFromIssuerWallet)) {
                    return true;
                }
            }
            return false;
        } catch (CantGetTransactionsException exception) {
            throw new DAPException(exception, "Checking the available balance in Asset issuer wallet", "Cannot get the transactions from Asset Issuer wallet");
        } catch (CantLoadWalletException exception) {
            throw new DAPException(exception, "Checking the available balance in Asset issuer wallet", "Cannot load the Asset Issuer wallet");
        }

    }

    public void setWalletPublicKey(String walletPublicKey) throws CantSetObjectException {
        if (walletPublicKey == null) {
            throw new CantSetObjectException("walletPublicKey is null");
        }
        this.walletPublicKey = "walletPublicKeyTest"; //TODO hardcoded value
        System.out.println("The wallet public key in vault is " + this.walletPublicKey);
    }

    public void setBitcoinCryptoNetworkManager(BitcoinNetworkManager bitcoinNetworkManager) {
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    public void setDigitalAssetMetadataAssetIssuerWalletTransaction(CryptoTransaction genesisTransaction, String internalId, AssetBalanceType assetBalanceType, TransactionType transactionType, DAPTransactionType dapTransactionType, String externalActorPublicKey) throws CantDeliverDigitalAssetToAssetWalletException {
        try {
            DigitalAssetMetadata digitalAssetMetadataToDeliver = getDigitalAssetMetadataFromLocalStorage(internalId);
            BalanceType balanceType = BalanceType.BOOK;
            if (assetBalanceType.getCode().equals(AssetBalanceType.BOOK.getCode())) {
                balanceType = BalanceType.BOOK;
            }
            if (assetBalanceType.getCode().equals(AssetBalanceType.AVAILABLE.getCode())) {
                balanceType = BalanceType.AVAILABLE;
            }
            System.out.println("ASSET Distribution OR RECEPTION - DELIVER TO WALLET TEST - " + balanceType + "\nHash: " + genesisTransaction.getTransactionHash());
            deliverDigitalAssetMetadata(digitalAssetMetadataToDeliver, genesisTransaction, balanceType, transactionType, dapTransactionType, externalActorPublicKey);
        } catch (CantGetDigitalAssetFromLocalStorageException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the DigitalAssetMetadata from storage");
        } catch (CantGetTransactionsException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Asset Transaction");
        } catch (CantLoadWalletException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot load the Asset Wallet");
        } catch (CantRegisterCreditException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot register credit in asset issuer wallet");
        } catch (CantRegisterDebitException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot register debit in asset issuer wallet");
        } catch (CantGetAssetIssuerActorsException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Actor Asset Issuer");
        } catch (CantAssetUserActorNotFoundException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot find the Actor Asset User");
        } catch (CantGetAssetUserActorsException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Actor Asset User");
        }
    }

    private void deliverDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata, CryptoTransaction genesisTransaction, BalanceType balanceType, TransactionType transactionType, DAPTransactionType dapTransactionType, String externalActorPublicKey) throws CantLoadWalletException, CantGetTransactionsException, CantRegisterCreditException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantAssetUserActorNotFoundException, CantGetAssetUserActorsException {
        String actorFromPublicKey = "ActorFromPublicKey";
        String actorToPublicKey = "ActorToPublicKey";
        if (dapTransactionType.getCode().equals(DAPTransactionType.DISTRIBUTION.getCode())) {
            AssetIssuerWallet assetWallet = this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey);
            AssetIssuerWalletBalance assetIssuerWalletBalance = assetWallet.getBookBalance(balanceType);
            actorFromPublicKey = this.actorAssetIssuerManager.getActorAssetIssuer().getActorPublicKey();
            System.out.println("ASSET DISTRIBUTION Actor Issuer public key:" + actorFromPublicKey);
            System.out.println("ASSET Distribution Transaction to deliver: " + genesisTransaction.getTransactionHash());
            AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper = new AssetIssuerWalletTransactionRecordWrapper(
                    digitalAssetMetadata,
                    genesisTransaction,
                    actorFromPublicKey,
                    actorToPublicKey
            );
            System.out.println("ASSET Distribution AssetIssuerWalletTransactionRecordWrapper: " + assetIssuerWalletTransactionRecordWrapper.getDescription());
            System.out.println("ASSET Distribution Balance Type: " + balanceType);
            System.out.println("ASSET Distribution Transaction Type: " + transactionType);
            if (transactionType.getCode().equals(TransactionType.CREDIT.getCode())) {
                assetIssuerWalletBalance.credit(assetIssuerWalletTransactionRecordWrapper, balanceType);
            }
            if (transactionType.getCode().equals(TransactionType.DEBIT.getCode())) {
                assetIssuerWalletBalance.debit(assetIssuerWalletTransactionRecordWrapper, balanceType);
            }
        }
        if (dapTransactionType.getCode().equals(DAPTransactionType.RECEPTION.getCode())) {
            AssetUserWallet assetWallet = this.assetUserWalletManager.loadAssetUserWallet(this.walletPublicKey);
            AssetUserWalletBalance assetUserWalletBalance = assetWallet.getBookBalance(balanceType);
            actorToPublicKey = this.actorAssetUserManager.getActorAssetUser().getActorPublicKey();
            System.out.println("ASSET RECEPTION Actor Issuer public key:" + actorToPublicKey);
            System.out.println("ASSET RECEPTION Transaction to deliver: " + genesisTransaction.getTransactionHash());
            AssetUserWalletTransactionRecordWrapper assetUserWalletTransactionRecordWrapper = new AssetUserWalletTransactionRecordWrapper(
                    digitalAssetMetadata,
                    genesisTransaction,
                    actorFromPublicKey,
                    actorToPublicKey
            );
            System.out.println("ASSET RECEPTION AssetIssuerWalletTransactionRecordWrapper: " + assetUserWalletTransactionRecordWrapper.getDescription());
            System.out.println("ASSET RECEPTION Balance Type: " + balanceType);
            System.out.println("ASSET RECEPTION Transaction Type: " + transactionType);
            if (transactionType.getCode().equals(TransactionType.CREDIT.getCode())) {
                assetUserWalletBalance.credit(assetUserWalletTransactionRecordWrapper, balanceType);
            }
            if (transactionType.getCode().equals(TransactionType.DEBIT.getCode())) {
                assetUserWalletBalance.debit(assetUserWalletTransactionRecordWrapper, balanceType);
            }
        }

    }
}
