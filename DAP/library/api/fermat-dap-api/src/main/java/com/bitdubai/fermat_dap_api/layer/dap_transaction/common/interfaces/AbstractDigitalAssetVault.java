package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
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
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
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
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
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
    protected AssetUserWalletManager assetUserWalletManager;
    protected final String digitalAssetFileName = "digitalAsset";
    protected final String digitalAssetMetadataFileName = "digitalAssetMetadata";
    protected String LOCAL_STORAGE_PATH = "digital-asset-swap";
    protected FileLifeSpan FILE_LIFE_SPAN = FileLifeSpan.PERMANENT;
    protected FilePrivacy FILE_PRIVACY = FilePrivacy.PRIVATE;
    protected UUID pluginId;
    protected PluginFileSystem pluginFileSystem;
    protected String digitalAssetFileStoragePath;
    protected AssetIssuerWalletManager assetIssuerWalletManager;
    protected String walletPublicKey = WalletUtilities.WALLET_PUBLIC_KEY;
    protected ActorAssetIssuerManager actorAssetIssuerManager;
    protected ActorAssetUserManager actorAssetUserManager;
    protected BitcoinNetworkManager bitcoinNetworkManager;

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

    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) {
        this.actorAssetUserManager = actorAssetUserManager;
    }

    public void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) {
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
            persistXMLStringInLocalStorage(digitalAssetInnerXML, digitalAssetFileName, digitalAsset.getPublicKey());
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
        try {
            String digitalAssetInnerXML = digitalAsset.toString();
            persistXMLStringInLocalStorage(digitalAssetInnerXML, digitalAssetFileName, digitalAssetMetadata.getDigitalAsset().getPublicKey());
            String digitalAssetMetadataInnerXML = digitalAssetMetadata.toString();
            persistXMLStringInLocalStorage(digitalAssetMetadataInnerXML, digitalAssetMetadataFileName, internalId);
        } catch (CantPersistFileException | CantCreateFileException exception) {
            throw new CantCreateDigitalAssetFileException(exception, "Persisting the digital asset objects in local storage", "Cannot create or persist the file");
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
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException
     */
    public DigitalAssetMetadata getDigitalAssetMetadataFromLocalStorage(String internalId) throws CantGetDigitalAssetFromLocalStorageException {
        try {
            PluginTextFile digitalAssetMetadataFile = this.pluginFileSystem.getTextFile(this.pluginId, digitalAssetMetadataFileName, internalId, FILE_PRIVACY, FILE_LIFE_SPAN);
            String digitalAssetMetadataXMLString = digitalAssetMetadataFile.getContent();
            return (DigitalAssetMetadata) XMLParser.parseXML(digitalAssetMetadataXMLString, new DigitalAssetMetadata());
        } catch (FileNotFoundException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot find " + internalId + "' file");
        } catch (CantCreateFileException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot create " + internalId + "' file");
        } catch (Exception exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Unexpected exception getting " + internalId + "' file");
        }
    }

    public DigitalAsset getDigitalAssetFromLocalStorage(String assetPk) throws CantGetDigitalAssetFromLocalStorageException {
        try {
            PluginTextFile digitalAssetMetadataFile = this.pluginFileSystem.getTextFile(this.pluginId, digitalAssetFileName, assetPk, FILE_PRIVACY, FILE_LIFE_SPAN);
            String digitalAssetMetadataXMLString = digitalAssetMetadataFile.getContent();
            return (DigitalAsset) XMLParser.parseXML(digitalAssetMetadataXMLString, new DigitalAsset());
        } catch (FileNotFoundException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot find " + assetPk + "' file");
        } catch (CantCreateFileException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot create " + assetPk + "' file");
        } catch (Exception exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Unexpected exception getting " + assetPk + "' file");
        }
    }

    /**
     * This method delete a XML file from the local storage
     *
     * @param internalId Asset Issuing: This id is an UUID provided by DigitalAssetTransactionFactory, this will be used to identify the file in Local Storage
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException
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

    public boolean isAssetTransactionHashAvailableBalanceInAssetWallet(String genesisTransactionHash, String assetPublicKey, BlockchainNetworkType networkType) throws DAPException {
        try {
            AssetIssuerWallet assetIssuerWallet = this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey, networkType);
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
        System.out.println("The wallet public key in vault is " + this.walletPublicKey);
    }

    public void setBitcoinCryptoNetworkManager(BitcoinNetworkManager bitcoinNetworkManager) {
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    public DigitalAssetMetadata updateMetadataTransactionChain(String genesisTx, CryptoTransaction cryptoTransaction) throws CantCreateDigitalAssetFileException, CantGetDigitalAssetFromLocalStorageException {
        return updateMetadataTransactionChain(genesisTx, cryptoTransaction.getTransactionHash(), cryptoTransaction.getBlockHash(), cryptoTransaction.getBlockchainNetworkType());
    }


    public DigitalAssetMetadata updateMetadataTransactionChain(String genesisTx, String txHash, String blockHash, BlockchainNetworkType blockchainNetworkType) throws CantCreateDigitalAssetFileException, CantGetDigitalAssetFromLocalStorageException {
        DigitalAssetMetadata digitalAssetMetadata = getDigitalAssetMetadataFromLocalStorage(genesisTx);
        digitalAssetMetadata.addNewTransaction(txHash, blockHash);
        persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, genesisTx);
        return digitalAssetMetadata;
    }

    public void updateWalletBalance(DigitalAssetMetadata digitalAssetMetadata,
                                    CryptoTransaction genesisTransaction,
                                    BalanceType balanceType,
                                    TransactionType transactionType,
                                    DAPTransactionType dapTransactionType,
                                    String externalActorPublicKey,
                                    Actors externalActorType,
                                    String memo) throws CantLoadWalletException, CantGetTransactionsException, CantRegisterCreditException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantAssetUserActorNotFoundException, CantGetAssetUserActorsException {
        String mySelf;
        BlockchainNetworkType networkType = genesisTransaction.getBlockchainNetworkType();
        switch (dapTransactionType) {
            case DISTRIBUTION: {
                AssetIssuerWallet issuerWallet = this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey, networkType);
                AssetIssuerWalletBalance assetIssuerWalletBalance = issuerWallet.getBalance();
                mySelf = this.actorAssetIssuerManager.getActorAssetIssuer().getActorPublicKey();
                Actors mySelfType = Actors.DAP_ASSET_ISSUER;
                if (Validate.isObjectNull(externalActorPublicKey)) {
                    externalActorPublicKey = mySelf;
                    externalActorType = mySelfType;
                }
                switch (transactionType) {
                    case CREDIT: {
                        AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper = new AssetIssuerWalletTransactionRecordWrapper(
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
                        AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper = new AssetIssuerWalletTransactionRecordWrapper(
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
                AssetUserWallet userWallet = this.assetUserWalletManager.loadAssetUserWallet(this.walletPublicKey, networkType);
                AssetUserWalletBalance assetUserWalletBalance = userWallet.getBalance();
                mySelf = this.actorAssetUserManager.getActorAssetUser().getActorPublicKey();
                Actors mySelfType = Actors.DAP_ASSET_USER;
                if (Validate.isObjectNull(externalActorPublicKey)) {
                    externalActorPublicKey = mySelf;
                    externalActorType = mySelfType;
                }
                switch (transactionType) {
                    case DEBIT: {
                        AssetUserWalletTransactionRecordWrapper assetUserWalletTransactionRecordWrapper = new AssetUserWalletTransactionRecordWrapper(
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
                        AssetUserWalletTransactionRecordWrapper assetUserWalletTransactionRecordWrapper = new AssetUserWalletTransactionRecordWrapper(
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
