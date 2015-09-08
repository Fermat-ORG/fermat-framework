package com.bitdubai.fermat_dap_api.layer.module.asset_issuer.interfaces;

import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;

import java.util.List;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetIssuerManager {
    //Falta documentar y colocar las excepciones
    List<AssetIssuer> getAllAssetIssuer();
    List<AssetIssuer> getAssetIssuerByIssuer(String issuerPublicKey);
    List<AssetIssuer> getAssetIssuerByState(State state);

    AssetIssuer createEmptyAssetIssuer();

    void createAssetIssuer(AssetIssuer assetIssuer);
    void removeAssetIssuer(AssetIssuer assetIssuer);
}
