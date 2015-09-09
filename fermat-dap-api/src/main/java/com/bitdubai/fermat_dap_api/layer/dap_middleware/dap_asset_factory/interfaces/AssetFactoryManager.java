package com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces;

import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantLoadAsserIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetIssuerException;

import java.util.List;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetFactoryManager {
    //Falta documentar y colocar las excepciones
    List<AssetFactory> getAllAssetIssuer() throws CantLoadAsserIssuerException;
    List<AssetFactory> getAssetIssuerByIssuer(String issuerPublicKey) throws CantLoadAsserIssuerException;
    List<AssetFactory> getAssetIssuerByState(State state) throws CantLoadAsserIssuerException;

    AssetFactory createEmptyAssetIssuer();
    void createAssetIssuer(AssetFactory assetFactory) throws CantCreateAssetIssuerException;
    void saveAssetIssuer(AssetFactory assetFactory) throws CantSaveAssetIssuerException;
    void removeAssetIssuer(AssetFactory assetFactory);

    //Este metodo sera el que se comunique con la capa transaccional a traves del DealsWithAssetIssuing
    boolean verifiedGenesisAmount(AssetFactory assetFactory);

    //Este metodo sera el que se comunique con la capa transaccional a traves del DealsWithAssetIssuing
    long getEstimatedFeeValue(AssetFactory assetFactory);

    //Este metodo sera el que se comunique con la capa transaccional a traves del DealsWithAssetIssuing
    void IssueAsset(AssetFactory assetFactory);
}
