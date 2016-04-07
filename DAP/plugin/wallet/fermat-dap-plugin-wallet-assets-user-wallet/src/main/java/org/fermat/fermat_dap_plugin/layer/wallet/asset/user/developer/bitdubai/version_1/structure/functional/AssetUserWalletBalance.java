package org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.functional;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;

/**
 * Created by franklin on 05/10/15.
 */
public class AssetUserWalletBalance implements AssetUserWalletList {

    DigitalAsset digitalAsset;
    long quantityBookBalance;
    long quantityAvailableBalance;
    long availableBalance;
    long bookBalance;
    int lockedAssets;

    public AssetUserWalletBalance(DigitalAsset digitalAsset, long quantityBookBalance, long quantityAvailableBalance, long availableBalance, long bookBalance) {
        this.digitalAsset = digitalAsset;
        this.quantityBookBalance = quantityBookBalance;
        this.quantityAvailableBalance = quantityAvailableBalance;
        this.availableBalance = availableBalance;
        this.bookBalance = bookBalance;
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
    public int getLockedAssets() {
        return lockedAssets;
    }

    @Override
    public void setLockedAssets(int lockedAssets) {
        this.lockedAssets = lockedAssets;
    }

    @Override
    public DigitalAsset getDigitalAsset() {
        return digitalAsset;
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
