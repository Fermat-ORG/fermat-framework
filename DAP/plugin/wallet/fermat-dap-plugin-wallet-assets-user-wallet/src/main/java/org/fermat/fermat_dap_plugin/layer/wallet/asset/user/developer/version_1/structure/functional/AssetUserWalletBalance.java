package org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by franklin on 05/10/15.
 */
public class AssetUserWalletBalance implements AssetUserWalletList, Serializable {

    DigitalAsset digitalAsset;
    Set<CryptoAddress> cryptoAddresses;
    long quantityBookBalance;
    long quantityAvailableBalance;
    long availableBalance;
    long bookBalance;
    int lockedAssets;

    public AssetUserWalletBalance(DigitalAsset digitalAsset, Set<CryptoAddress> cryptoAddresses, long quantityBookBalance, long quantityAvailableBalance, long availableBalance, long bookBalance, int lockedAssets) {
        this.digitalAsset = digitalAsset;
        this.cryptoAddresses = cryptoAddresses;
        this.quantityBookBalance = quantityBookBalance;
        this.quantityAvailableBalance = quantityAvailableBalance;
        this.availableBalance = availableBalance;
        this.bookBalance = bookBalance;
        this.lockedAssets = lockedAssets;
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
    public List<CryptoAddress> getAddresses() {
        return new ArrayList<>(cryptoAddresses);
    }

    @Override
    public void setAddresses(Set<CryptoAddress> addresses) {
        this.cryptoAddresses = addresses;
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
