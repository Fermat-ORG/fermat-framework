package com.bitdubai.fermat_dap_api.layer.dap_asset_issuer.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;

import java.sql.Timestamp;

/**
 * Created by franklin on 05/09/15.
 */
public interface AssetIssuer {

    String getwalletPublicKey();

    Timestamp getCreationTimestamp();

    String getAssetUserIdentityPublicKey();

    DigitalAsset getDigitalAsset();

    WalletCategory getWalletCategory();

    WalletType getWalletType();
}
