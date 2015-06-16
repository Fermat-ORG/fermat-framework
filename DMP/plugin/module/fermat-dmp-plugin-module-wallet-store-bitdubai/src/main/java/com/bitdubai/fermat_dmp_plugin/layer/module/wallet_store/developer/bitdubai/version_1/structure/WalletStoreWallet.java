package com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.Wallet;

import java.util.UUID;

/**
 * @author rodrigo
 * Created by rodrigo on 08/05/15.
 */
public class WalletStoreWallet implements Wallet {

    private UUID walletId;

    private String title;

    private String description;

    private String picture;

    private String company;

    private String Open_hours;

    private String Address;

    private String Phone;

    private float rate;

    private int value;

    private float favorite;

    private float sale;

    private float timetoarraive;

    private boolean installed;

    @Override
    public UUID getWalletId() {
        return walletId;
    }

    @Override
    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getPicture() {
        return picture;
    }

    @Override
    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String getCompany() {
        return company;
    }

    @Override
    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String getOpen_hours() {
        return Open_hours;
    }

    @Override
    public void setOpen_hours(String open_hours) {
        Open_hours = open_hours;
    }

    @Override
    public String getAddress() {
        return Address;
    }

    @Override
    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String getPhone() {
        return Phone;
    }

    @Override
    public void setPhone(String phone) {
        Phone = phone;
    }

    @Override
    public float getRate() {
        return rate;
    }

    @Override
    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public float getFavorite() {
        return favorite;
    }

    @Override
    public void setFavorite(float favorite) {
        this.favorite = favorite;
    }

    @Override
    public float getSale() {
        return sale;
    }

    @Override
    public void setSale(float sale) {
        this.sale = sale;
    }

    @Override
    public float getTimetoarraive() {
        return timetoarraive;
    }

    @Override
    public void setTimetoarraive(float timetoarraive) {
        this.timetoarraive = timetoarraive;
    }

    @Override
    public boolean isInstalled() {
        return installed;
    }

    @Override
    public void setInstalled(boolean installed) {
        this.installed = installed;
    }
}
