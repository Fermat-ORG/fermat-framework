package com.bitdubai.fermat_dap_api.layer.module.asset_issuer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetIssuer {
    //Esta interfaz tendra que contener todos las propiedades que se van a pedir en la sub app, para que se guarde el objeto digital asset
    //en su creacion.
    //Falta los seters
    String getwalletPublicKey();

    String getAssetUserIdentityPublicKey();

    DigitalAsset getDigitalAsset();

    WalletCategory getWalletCategory();

    WalletType getWalletType();
}
