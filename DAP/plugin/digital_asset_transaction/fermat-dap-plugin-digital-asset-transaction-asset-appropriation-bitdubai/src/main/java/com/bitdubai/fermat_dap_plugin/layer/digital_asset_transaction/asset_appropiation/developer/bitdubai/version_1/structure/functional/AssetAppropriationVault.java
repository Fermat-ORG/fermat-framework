package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public class AssetAppropriationVault {

    //VARIABLE DECLARATION

    private final UUID pluginId;
    private final PluginFileSystem pluginFileSystem;
    private static final String STORAGE_PATH = "digital-asset-appropriation/";
    private static final FileLifeSpan FILE_LIFE_SPAN = FileLifeSpan.PERMANENT;
    //TODO CHANGE PRIVACY TO PRIVATE
    private static final FilePrivacy FILE_PRIVACY = FilePrivacy.PUBLIC;

    //CONSTRUCTORS

    public AssetAppropriationVault(UUID pluginId, PluginFileSystem pluginFileSystem) throws CantSetObjectException {
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.pluginFileSystem = Validate.verifySetter(pluginFileSystem, "pluginFileSystem is null");
    }

    //PUBLIC METHODS

    public void persistDigitalAssetInLocalStorage(DigitalAsset digitalAsset) throws CantCreateDigitalAssetFileException {
        try {
            PluginTextFile digitalAssetFile = pluginFileSystem.createTextFile(pluginId, STORAGE_PATH, createFilename(digitalAsset.getPublicKey()), FILE_PRIVACY, FILE_LIFE_SPAN);
            digitalAssetFile.setContent(digitalAsset.toString());
            digitalAssetFile.persistToMedia();

        } catch (CantPersistFileException | CantCreateFileException exception) {
            throw new CantCreateDigitalAssetFileException(exception, "Persisting the digital asset objects in local storage", "Cannot create or persist the file");
        }
    }

    //PRIVATE METHODS

    private String createFilename(String digitalAssetPublicKey) {
        return digitalAssetPublicKey + ".xml";
    }

    //GETTER AND SETTERS

    public DigitalAsset getDigitalAssetFromLocalStorage(String assetPublicKey) throws CantGetDigitalAssetFromLocalStorageException {
        try {

            PluginTextFile digitalAssetFile = pluginFileSystem.getTextFile(pluginId, STORAGE_PATH, createFilename(assetPublicKey), FILE_PRIVACY, FILE_LIFE_SPAN);
            System.out.println(digitalAssetFile.getContent());
            return (DigitalAsset) XMLParser.parseXML(digitalAssetFile.getContent(), new DigitalAsset());
        } catch (FileNotFoundException e) {
            throw new CantGetDigitalAssetFromLocalStorageException(e, "Getting Digital Asset file from local storage", "Unexpected exception getting '" + STORAGE_PATH + createFilename(assetPublicKey) + "' file");
        } catch (CantCreateFileException e) {
            throw new CantGetDigitalAssetFromLocalStorageException(e, "Getting Digital Asset file from local storage", "Unexpected exception creating '" + STORAGE_PATH + createFilename(assetPublicKey) + "' file");
        }
    }

    //INNER CLASSES
}
