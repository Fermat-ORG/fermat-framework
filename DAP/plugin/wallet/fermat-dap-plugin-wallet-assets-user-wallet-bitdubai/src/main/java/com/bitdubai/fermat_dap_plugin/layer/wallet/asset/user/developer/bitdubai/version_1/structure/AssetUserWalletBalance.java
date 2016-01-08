package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;

/**
 * Created by franklin on 05/10/15.
 */
public class AssetUserWalletBalance implements AssetUserWalletList {

    DigitalAsset digitalAsset;
    long quantityBookBalance;
    long quantityAvailableBalance;

    public AssetUserWalletBalance(DigitalAsset digitalAsset, long quantityBookBalance, long quantityAvailableBalance) {
        this.digitalAsset = digitalAsset;
        this.quantityBookBalance = quantityBookBalance;
        this.quantityAvailableBalance = quantityAvailableBalance;
    }

    public AssetUserWalletBalance() {
    }

    @Override
    public long getQuantityAvailableBalance() {
        return quantityAvailableBalance;
    }

    @Override
    public void setQuantityAvailableBalance(long quantityAvailableBalance) {
        this.quantityAvailableBalance = quantityAvailableBalance;
    }

    @Override
    public DigitalAsset getDigitalAsset() {
        return digitalAsset;
    }

    @Override
    public void setDigitalAsset(DigitalAsset digitalAsset) {
        this.digitalAsset = digitalAsset;
    }

    @Override
    public long getQuantityBookBalance() {
        return quantityBookBalance;
    }

    @Override
    public void setQuantityBookBalance(long quantityBookBalance) {
        this.quantityBookBalance = quantityBookBalance;
    }
}
