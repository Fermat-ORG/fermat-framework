package com.bitdubai.fermat_dap_plugin.layer.sub_app_module.asset.factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.BlockchainNetworkType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.DealsWithAssetFactory;

import java.util.List;

/**
 * Created by franklin on 20/09/15.
 */
public class AssetFactorySupAppModuleManager implements DealsWithAssetFactory {
    AssetFactoryManager assetFactoryManager;
    @Override
    public void setAssetFactoryManager(AssetFactoryManager assetFactoryManager) {
        this.assetFactoryManager = assetFactoryManager;
    }
    /**
     * constructor
     * @param assetFactoryManager
     */
    public AssetFactorySupAppModuleManager(AssetFactoryManager assetFactoryManager) {
        this.assetFactoryManager = assetFactoryManager;
    }

    public AssetFactory getAssetFactory(String assetPublicKey)  throws CantGetAssetFactoryException {
        return assetFactoryManager.getAssetFactoryByPublicKey(assetPublicKey);
    }

    public AssetFactory newAssetFactoryEmpty()  throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException {
        return assetFactoryManager.createEmptyAssetFactory();
    }

    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException {
        assetFactoryManager.saveAssetFactory(assetFactory);
    }

    public void publishAssetFactory(AssetFactory assetFactory, BlockchainNetworkType blockchainNetworkType)  throws CantSaveAssetFactoryException {
        assetFactoryManager.publishAsset(assetFactory, blockchainNetworkType);
    }

    public List<AssetFactory> getAssetsFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException {
        return assetFactoryManager.getAssetFactoryByIssuer(issuerIdentityPublicKey);
    }

    public List<AssetFactory> getAssetsFactoryByState(State state) throws CantGetAssetFactoryException {
        return assetFactoryManager.getAssetFactoryByState(state);
    }

    public List<AssetFactory> getAssetsFactoryAll() throws CantGetAssetFactoryException {
        return assetFactoryManager.getAssetFactoryAll();
    }

}
