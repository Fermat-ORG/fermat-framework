package com.bitdubai.fermat_dap_api.layer.module.dap_asset_issuer_sub_app_module.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;

import java.sql.Timestamp;

/**
 * Created by franklin on 05/09/15.
 */
public interface AssetIssuer {

    String getwalletPublicKey();
    void setwalletPublicKey(String publicKey);

    Timestamp getCreationTimestamp();
    void setCreationTimestamp(Timestamp creationTimestamp);

    String getActorIssuer(String issuerIdentityPublicKey,
                          Actors typeActorIssuer

    );
    void setActorIssuer(String issuerIdentityPublicKey,
                        Actors typeActorIssuer

    );

    DigitalAsset getDigitalAsset();
    void setDigitalAsset(DigitalAsset digitalAsset);

    WalletCategory getWalletCategory();
    void setWalletCategory(WalletCategory walletCategory);

    WalletType getWalletType();
    void setWalletType(WalletType walletType);
}