package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;

/**
 * Created by franklin on 29/09/15.
 */
public interface AssetIssuerWalletList {

    DigitalAsset getDigitalAsset();

    void setDigitalAsset(DigitalAsset digitalAsset);

    long getBookBalance();

    void setBookBalance(long bookBalance);

    long getAvailableBalance();

    void setAvailableBalance(long availableBalance);

    long getQuantityBookBalance();

    void setQuantityBookBalance(long quantityBookBalance);

    long getQuantityAvailableBalance();

    void setQuantityAvailableBalance(long quantityAvailableBalance);
}
