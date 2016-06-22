package org.fermat.fermat_dap_plugin.layer.wallet.asset.issuer.developer.version_1.structure.functional;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;

import java.io.Serializable;

/**
 * Created by franklin on 29/09/15.
 */
public class AssetIssuerWalletBalance implements AssetIssuerWalletList, Serializable {

    private DigitalAsset digitalAsset;
    private long bookBalance;
    private long availableBalance;
    private long quantityBookBalance;
    private long quantityAvailableBalance;


    public AssetIssuerWalletBalance() {
    }

    public AssetIssuerWalletBalance(DigitalAsset digitalAsset, long bookBalance, long availableBalance, long quantityBookBalance, long quantityAvailableBalance) {
        this.digitalAsset = digitalAsset;
        this.bookBalance = bookBalance;
        this.availableBalance = availableBalance;
        this.quantityBookBalance = quantityBookBalance;
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
    public long getBookBalance() {
        return bookBalance;
    }

    @Override
    public void setBookBalance(long bookBalance) {
        this.bookBalance = bookBalance;
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
    public long getQuantityBookBalance() {
        return quantityBookBalance;
    }

    @Override
    public void setQuantityBookBalance(long quantityBookBalance) {
        this.quantityBookBalance = quantityBookBalance;
    }

    @Override
    public long getQuantityAvailableBalance() {
        return quantityAvailableBalance;
    }

    @Override
    public void setQuantityAvailableBalance(long quantityAvailableBalance) {
        this.quantityAvailableBalance = quantityAvailableBalance;
    }

}
