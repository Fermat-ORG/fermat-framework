package com.bitdubai.fermat_dap_api.layer.module.dap_asset_issuer_sub_app_module.interfaces;

import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;

import java.util.List;

/**
 * Created by franklin on 05/09/15.
 */
public interface AssetIssuerManager {
    //Falta documentar y colocar las excepciones
    List<AssetIssuer> getAllAssetIssuer();
    List<AssetIssuer> getAssetIssuerByIssuer(String issuerPublicKey);
    List<AssetIssuer> getAssetIssuerByState(State state);
    AssetIssuer getAssetIssuer(String AssetIssuerPublicKey);

    AssetIssuer createEmptyAssetIssuer();
    void saveAssetIssuer(AssetIssuer assetIssuer);
    void setStateAssetIssuer(AssetIssuer assetIssuer);
    void removeAssetIssuer(AssetIssuer assetIssuer);
}
