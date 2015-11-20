package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces;

/**
 * Created by franklin on 05/10/15.
 */
public interface AssetUserWalletList {
    public String getAssetPublicKey();
    public void setAssetPublicKey(String assetPublicKey);

    public String getDescription();
    public void setDescription(String description);

    public String getName();
    public void setName(String name);

    public long getBookBalance();
    public void setBookBalance(long bookBalance);

    public long getAvailableBalance();
    public void setAvailableBalance(long availableBalance);

    public long getQuantityBookBalance();
    public void setQuantityBookBalance(long quantityBookBalance);

    public long getQuantityAvailableBalance();
    public void setQuantityAvailableBalance(long quantityAvailableBalance);
}
