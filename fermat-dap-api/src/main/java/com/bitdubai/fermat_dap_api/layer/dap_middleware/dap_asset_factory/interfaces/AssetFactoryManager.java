package com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces;

import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;

import java.util.List;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetFactoryManager {
    //Geters Asset Factory
    List<AssetFactory> getAllAssetIssuer() throws CantGetAssetFactoryException;
    List<AssetFactory> getAssetIssuerByIssuer(String issuerPublicKey) throws CantGetAssetFactoryException;
    List<AssetFactory> getAssetIssuerByState(State state) throws CantGetAssetFactoryException;

    //CRUD
    AssetFactory createEmptyAssetIssuer();
    void createAssetIssuer(AssetFactory assetFactory) throws CantCreateAssetFactoryException;
    void saveAssetIssuer(AssetFactory assetFactory) throws CantSaveAssetFactoryException;
    void removeAssetIssuer(AssetFactory assetFactory) throws CantDeleteAsserFactoryException;

    //Deals
    //TODO: Revisar
    boolean verifiedGenesisAmount(AssetFactory assetFactory);

    //TODO: Revisar
    long getEstimatedFeeValue(AssetFactory assetFactory);

    //TODO: Revisar
    void IssueAsset(AssetFactory assetFactory);
}
