package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/10/15.
 */
public abstract class DigitalAssetVault {

    //public String LOCAL_STORAGE_PATH="digital-asset-metadata/";
    public final String digitalAssetFileName="digital-asset.xml";
    public final String digitalAssetMetadataFileName="digital-asset-metadata.xml";
    public String LOCAL_STORAGE_PATH="digital-asset-swap/";
    public FileLifeSpan FILE_LIFE_SPAN=FileLifeSpan.PERMANENT;
    //For testing I'm gonna use this type of privacy, change to PRIVATE in production release
    public FilePrivacy FILE_PRIVACY=FilePrivacy.PUBLIC;
    public UUID pluginId;
    public PluginFileSystem pluginFileSystem;
    public String digitalAssetFileStoragePath;
    public AssetIssuerWalletManager assetIssuerWalletManager;
    public String walletPublicKey;

    /**
     * Set the UUID from this plugin
     * @param pluginId
     * @throws CantSetObjectException
     */
    public void setPluginId(UUID pluginId) throws CantSetObjectException{
        if(pluginId==null){
            throw new CantSetObjectException("pluginId is null");
        }
        this.pluginId=pluginId;
    }

    /**
     * Set the PliginFileSystem used to persist Digital Assets in local storage
     * @param pluginFileSystem
     * @throws CantSetObjectException
     */
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException{
        if(pluginFileSystem==null){
            throw new CantSetObjectException("pluginFileSystem is null");
        }
        this.pluginFileSystem=pluginFileSystem;
    }

    /**
     * This method persists the DigitalAssetMetadata XML file in local storage.
     * @param digitalAssetMetadata
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException
     */
    public void persistDigitalAssetMetadataInLocalStorage(DigitalAssetMetadata digitalAssetMetadata)throws CantCreateDigitalAssetFileException {
        DigitalAsset digitalAsset=digitalAssetMetadata.getDigitalAsset();
        try{
            String digitalAssetInnerXML=digitalAsset.toString();
            persistXMLStringInLocalStorage(digitalAssetInnerXML, digitalAssetFileName);
            String digitalAssetMetadataInnerXML=digitalAssetMetadata.toString();
            persistXMLStringInLocalStorage(digitalAssetMetadataInnerXML, digitalAssetMetadataFileName);
        } catch (CantPersistFileException | CantCreateFileException exception) {
            throw new CantCreateDigitalAssetFileException(exception, "Persisting the digital asset objects in local storage", "Cannot create or persist the file");
        }

    }

    private void persistXMLStringInLocalStorage(String innerXML, String fileName) throws CantCreateFileException, CantPersistFileException {
        PluginTextFile digitalAssetFile=this.pluginFileSystem.createTextFile(this.pluginId, this.digitalAssetFileStoragePath, fileName, this.FILE_PRIVACY, this.FILE_LIFE_SPAN);
        digitalAssetFile.setContent(innerXML);
        digitalAssetFile.persistToMedia();
    }

    /**
     * This method get the XML file and cast the DigitalAssetMetadata object
     * @param genesisTransaction
     * @return
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException
     */
    public DigitalAssetMetadata getDigitalAssetMetadataFromLocalStorage(String genesisTransaction) throws CantGetDigitalAssetFromLocalStorageException {
        String digitalAssetMetadataFileName = "no-file-name-assigned";
        try {
            DigitalAssetMetadata digitalAssetMetadataObtainedFromFileStorage = new DigitalAssetMetadata();
            digitalAssetMetadataFileName = genesisTransaction + ".xml";
            PluginTextFile digitalAssetMetadataFile = this.pluginFileSystem.getTextFile(this.pluginId, this.LOCAL_STORAGE_PATH, digitalAssetMetadataFileName, FILE_PRIVACY, FILE_LIFE_SPAN);
            String digitalAssetMetadataXMLString = digitalAssetMetadataFile.getContent();
            digitalAssetMetadataObtainedFromFileStorage = (DigitalAssetMetadata) XMLParser.parseXML(digitalAssetMetadataXMLString, digitalAssetMetadataObtainedFromFileStorage);
            return digitalAssetMetadataObtainedFromFileStorage;
        } catch (FileNotFoundException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot find " + this.LOCAL_STORAGE_PATH + digitalAssetMetadataFileName + "' file");
        } catch (CantCreateFileException exception) {
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage", "Cannot create " + this.LOCAL_STORAGE_PATH + digitalAssetMetadataFileName + "' file");
        } catch (Exception exception){
            throw new CantGetDigitalAssetFromLocalStorageException(exception, "Getting Digital Asset file from local storage","Unexpected exception getting '"+this.LOCAL_STORAGE_PATH+digitalAssetMetadataFileName+"' file");
        }

    }

    /**
     * This method delete a XML file from the local storage
     * @param genesisTransaction
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException
     */
    public void deleteDigitalAssetMetadataFromLocalStorage(String genesisTransaction) throws CantDeleteDigitalAssetFromLocalStorageException {
        String digitalAssetMetadataFileName = "no-file-name-assigned";
        try{
            digitalAssetMetadataFileName = genesisTransaction + ".xml";
            PluginTextFile digitalAssetMetadataFile = this.pluginFileSystem.getTextFile(this.pluginId, this.LOCAL_STORAGE_PATH, digitalAssetMetadataFileName, FILE_PRIVACY, FILE_LIFE_SPAN);
            digitalAssetMetadataFile.delete();
        } catch (FileNotFoundException exception) {
            throw new CantDeleteDigitalAssetFromLocalStorageException(exception, "Deleting Digital Asset file from local storage", "Cannot find " + this.LOCAL_STORAGE_PATH + digitalAssetMetadataFileName + "' file");
        } catch (CantCreateFileException exception) {
            throw new CantDeleteDigitalAssetFromLocalStorageException(exception, "Deleting Digital Asset file from local storage", "Cannot create " + this.LOCAL_STORAGE_PATH + digitalAssetMetadataFileName + "' file");
        } catch (Exception exception){
            throw new CantDeleteDigitalAssetFromLocalStorageException(exception, "Deleting Digital Asset file from local storage","Unexpected exception getting '"+this.LOCAL_STORAGE_PATH+digitalAssetMetadataFileName+"' file");
        }
    }

    public void setDigitalAssetLocalFilePath(String digitalAssetFileStoragePath){
        this.digitalAssetFileStoragePath=digitalAssetFileStoragePath;
    }

    public void setAssetIssuerWalletManager(AssetIssuerWalletManager assetIssuerWalletManager) throws CantSetObjectException {
        if(assetIssuerWalletManager==null){
            throw new CantSetObjectException("assetIssuerWalletManager is null");
        }
        this.assetIssuerWalletManager=assetIssuerWalletManager;
    }

    public boolean isAssetTransactionHashAvailableBalanceInAssetWallet(String genesisTransactionHash, String assetPublicKey) throws DAPException{
        try{
            AssetIssuerWallet assetIssuerWallet=this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey);
            List<AssetIssuerWalletTransaction> assetIssuerWalletTransactionList = assetIssuerWallet.getTransactionsAll(
                    BalanceType.AVAILABLE,
                    TransactionType.CREDIT,
                    assetPublicKey);
            for(AssetIssuerWalletTransaction assetIssuerWalletTransaction : assetIssuerWalletTransactionList){
                String transactionHashFromIssuerWallet=assetIssuerWalletTransaction.getTransactionHash();
                if(genesisTransactionHash.equals(transactionHashFromIssuerWallet)){
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
        if(walletPublicKey==null){
            throw new CantSetObjectException("walletPublicKey is null");
        }
        this.walletPublicKey=walletPublicKey;
    }

}
