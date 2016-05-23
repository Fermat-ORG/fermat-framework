package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public class AssetAppropriationVault extends AbstractDigitalAssetVault{

    //VARIABLE DECLARATION

    private final UUID pluginId;
    private final PluginFileSystem pluginFileSystem;
    private static final String STORAGE_PATH = "digital-asset-appropriation";

    //CONSTRUCTORS

    public AssetAppropriationVault(UUID pluginId, PluginFileSystem pluginFileSystem) throws CantSetObjectException {
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.pluginFileSystem = Validate.verifySetter(pluginFileSystem, "pluginFileSystem is null");
    }

    //PUBLIC METHODS

    @Override
    public void persistDigitalAssetMetadataInLocalStorage(DigitalAssetMetadata assetMetadata, String internalId) throws CantCreateDigitalAssetFileException {
        try {
            PluginTextFile digitalAssetFile = pluginFileSystem.createTextFile(pluginId, STORAGE_PATH, internalId, FILE_PRIVACY, FILE_LIFE_SPAN);
            digitalAssetFile.setContent(assetMetadata.toString());
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

    @Override
    public DigitalAssetMetadata getDigitalAssetMetadataFromLocalStorage(String internalId) throws CantGetDigitalAssetFromLocalStorageException {
        try {
            PluginTextFile metadataFile = pluginFileSystem.getTextFile(pluginId, STORAGE_PATH, internalId, FILE_PRIVACY, FILE_LIFE_SPAN);
            return (DigitalAssetMetadata) XMLParser.parseXML(metadataFile.getContent(), new DigitalAssetMetadata());
        } catch (FileNotFoundException e) {
            throw new CantGetDigitalAssetFromLocalStorageException(e, "Getting Digital Asset file from local storage", "Unexpected exception getting '" + STORAGE_PATH + createFilename(internalId) + "' file");
        } catch (CantCreateFileException e) {
            throw new CantGetDigitalAssetFromLocalStorageException(e, "Getting Digital Asset file from local storage", "Unexpected exception creating '" + STORAGE_PATH + createFilename(internalId) + "' file");
        }
    }
    //INNER CLASSES
}
