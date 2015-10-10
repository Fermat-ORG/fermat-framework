package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/10/15.
 */
public interface DigitalAssetVault {

    String LOCAL_STORAGE_PATH="digital-asset-metadata/";
    FileLifeSpan FILE_LIFE_SPAN=FileLifeSpan.PERMANENT;
    //For testing I'm gonna use this type of privacy, change to PRIVATE in production release
    FilePrivacy FILE_PRIVACY=FilePrivacy.PUBLIC;

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
     * This method persists the DigitalAssetMetadata XML file in local storage.
     * @param digitalAssetMetadata
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException
     */
    void persistDigitalAssetMetadataInLocalStorage(DigitalAssetMetadata digitalAssetMetadata)throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;

    /**
     * This method get the XML file and cast the DigitalAssetMetadata object
     * @param genesisTransaction
     * @return
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException
     */
    DigitalAssetMetadata getDigitalAssetMetadataFromLocalStorage(String genesisTransaction) throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;

    /**
     * This method delete a XML file from the local storage
     * @param genesisTransaction
     * @throws com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException
     */
    void deleteDigitalAssetMetadataFromLocalStorage(String genesisTransaction) throws CantDeleteDigitalAssetFromLocalStorageException;

}
