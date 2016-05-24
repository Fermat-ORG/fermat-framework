package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;

import java.util.UUID;

/**
 * The purpose of this class is to store the asset metadata until the transaction is validated.
 * This kind of transactions can be rejected by several reasons and way before reaching an asset wallet.
 * So we'll store they in there and deleted if the transaction is rejected.
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 17/02/16.
 */
public class AssetBuyingVault extends AbstractDigitalAssetVault {

    //VARIABLE DECLARATION

    //CONSTRUCTORS
    public AssetBuyingVault(UUID pluginId, PluginFileSystem pluginFileSystem) throws CantSetObjectException {
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.pluginFileSystem = Validate.verifySetter(pluginFileSystem, "pluginFileSystem is null");
        LOCAL_STORAGE_PATH = "asset-buying";
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
