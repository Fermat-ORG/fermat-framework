package org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.functional;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;

import java.io.Serializable;

/**
 * Created by franklin on 15/10/15.
 */
public class AssetRedeemPointWalletBalance implements AssetRedeemPointWalletList, Serializable {

    DigitalAsset digitalAsset;
    long quantityBookBalance;
    long quantityAvailableBalance;
    long availableBalance;
    long bookBalance;

    public AssetRedeemPointWalletBalance(DigitalAsset digitalAsset, long quantityBookBalance, long quantityAvailableBalance, long availableBalance, long bookBalance) {
        this.digitalAsset = digitalAsset;
        this.quantityBookBalance = quantityBookBalance;
        this.quantityAvailableBalance = quantityAvailableBalance;
        this.availableBalance = availableBalance;
        this.bookBalance = bookBalance;
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
    public long getAvailableBalance() {
        return availableBalance;
    }

    @Override
    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }

    @Override
    public long getBookBalance() {
        return bookBalance;
    }

    @Override
    public void setBookBalance(long bookBalance) {
        this.bookBalance = bookBalance;
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
