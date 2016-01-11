package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;

/**
 * Created by franklin on 05/10/15.
 */
public interface AssetUserWalletList {

    DigitalAsset getDigitalAsset();

    void setDigitalAsset(DigitalAsset digitalAsset);

    long getQuantityBookBalance();

    void setQuantityBookBalance(long quantityBookBalance);

    long getQuantityAvailableBalance();

    void setQuantityAvailableBalance(long quantityAvailableBalance);
}
