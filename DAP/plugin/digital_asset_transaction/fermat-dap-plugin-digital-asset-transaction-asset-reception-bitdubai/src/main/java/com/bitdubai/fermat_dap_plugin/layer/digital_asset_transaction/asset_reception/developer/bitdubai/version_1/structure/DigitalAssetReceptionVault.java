package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.DigitalAssetVault;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/10/15.
 */
public class DigitalAssetReceptionVault implements DigitalAssetVault {
    @Override
    public void setPluginId(UUID pluginId) throws CantSetObjectException {

    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException {

    }

    @Override
    public void persistDigitalAssetMetadataInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException {

    }

    @Override
    public DigitalAssetMetadata getDigitalAssetMetadataFromLocalStorage(String genesisTransaction) throws CantGetDigitalAssetFromLocalStorageException {
        return null;
    }

    @Override
    public void deleteDigitalAssetMetadataFromLocalStorage(String genesisTransaction) throws CantDeleteDigitalAssetFromLocalStorageException {

    }
}
