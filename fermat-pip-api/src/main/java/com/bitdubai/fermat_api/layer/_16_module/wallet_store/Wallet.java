package com.bitdubai.fermat_api.layer._16_module.wallet_store;

import java.util.UUID;

/**
 * Created by rodrigo on 09/05/15.
 */
public interface Wallet {
    public UUID getWalletId();

    public void setWalletId(UUID walletId);

    public String getTitle();

    public void setTitle(String title);

    public String getDescription();

    public void setDescription(String description);

    public String getPicture();

    public void setPicture(String picture);

    public String getCompany();

    public void setCompany(String company);

    public String getOpen_hours();

    public void setOpen_hours(String open_hours);

    public String getAddress();

    public void setAddress(String address);

    public String getPhone();

    public void setPhone(String phone);

    public float getRate();

    public void setRate(float rate);

    public int getValue();

    public void setValue(int value);

    public float getFavorite();

    public void setFavorite(float favorite);

    public float getSale();

    public void setSale(float sale);

    public float getTimetoarraive();

    public void setTimetoarraive(float timetoarraive);

    public boolean isInstalled();

    public void setInstalled(boolean installed);
}
