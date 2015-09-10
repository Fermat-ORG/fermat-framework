package com.bitdubai.fermat_dap_api.layer.module.asset_issuer.interfaces;

import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums.State;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.exceptions.CantCreateAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.exceptions.CantLoadAsserIssuerException;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.exceptions.CantSaveAssetIssuerException;

import java.util.List;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetIssuerManager {
    //Falta documentar y colocar las excepciones
    List<AssetIssuer> getAllAssetIssuer() throws CantLoadAsserIssuerException;
    List<AssetIssuer> getAssetIssuerByIssuer(String issuerPublicKey) throws CantLoadAsserIssuerException;
    List<AssetIssuer> getAssetIssuerByState(State state) throws CantLoadAsserIssuerException;

    AssetIssuer createEmptyAssetIssuer();
    void createAssetIssuer(AssetIssuer assetIssuer) throws CantCreateAssetIssuerException;
    void saveAssetIssuer(AssetIssuer assetIssuer) throws CantSaveAssetIssuerException;
    void removeAssetIssuer(AssetIssuer assetIssuer);

    //Este metodo sera el que se comunique con la capa transaccional a traves del DealsWithAssetIssuing
    boolean verifiedGenesisAmount(AssetIssuer assetIssuer);

    //Este metodo sera el que se comunique con la capa transaccional a traves del DealsWithAssetIssuing
    long getEstimatedFeeValue(AssetIssuer assetIssuer);

    //Este metodo sera el que se comunique con la capa transaccional a traves del DealsWithAssetIssuing
    void IssueAsset(AssetIssuer assetIssuer);
}
