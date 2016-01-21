package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;

/**
 * Created by franklin on 15/10/15.
 */
public class AssetRedeemPointWalletBalance implements AssetRedeemPointWalletList {

    DigitalAsset digitalAsset;
    long quantityBookBalance;
    long quantityAvailableBalance;

    public AssetRedeemPointWalletBalance(DigitalAsset digitalAsset, long quantityBookBalance, long quantityAvailableBalance) {
        this.digitalAsset = digitalAsset;
        this.quantityBookBalance = quantityBookBalance;
        this.quantityAvailableBalance = quantityAvailableBalance;
    }

    public AssetRedeemPointWalletBalance() {
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
