package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
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
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.DigitalAssetVault;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/10/15.
 */
public class DigitalAssetTransmissionVault implements DigitalAssetVault {

    PluginFileSystem pluginFileSystem;
    UUID pluginId;
    ErrorManager errorManager;
    private final String LOCAL_STORAGE_PATH="digital-asset-transmission/";
    private final String digitalAssetFileName="digital-asset.xml";
    private final String digitalAssetMetadataFileName="digital-asset-metadata.xml";
    String digitalAssetFileStoragePath;

    public DigitalAssetTransmissionVault(UUID pluginId, PluginFileSystem pluginFileSystem, ErrorManager errorManager) throws CantSetObjectException {
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setErrorManager(errorManager);
    }

    @Override
    public void setPluginId(UUID pluginId) throws CantSetObjectException{
        if(pluginId==null){
            throw new CantSetObjectException("pluginId is null");
        }
        this.pluginId=pluginId;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException{
        if(pluginFileSystem==null){
            throw new CantSetObjectException("pluginFileSystem is null");
        }
        this.pluginFileSystem=pluginFileSystem;
    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException{
        if(errorManager==null){
            throw new CantSetObjectException("ErrorManager is null");
        }
        this.errorManager=errorManager;
    }

    @Override
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

    public void setDigitalAssetLocalFilePath(String digitalAssetFileStoragePath){
        this.digitalAssetFileStoragePath=digitalAssetFileStoragePath;
    }

    @Override
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

    @Override
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
}
