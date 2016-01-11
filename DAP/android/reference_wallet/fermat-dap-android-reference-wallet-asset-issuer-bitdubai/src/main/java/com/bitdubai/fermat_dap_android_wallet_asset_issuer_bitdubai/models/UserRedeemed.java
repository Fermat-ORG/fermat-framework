package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Penny on 01/08/16.
 */
public class UserRedeemed {
    private String userName;
    private Timestamp redeemedDate;
    private String redeemedStatus;

    public UserRedeemed() {

    }

    public UserRedeemed(String userName, Timestamp redeemedDate, String redeemedStatus) {
        this.userName = userName;
        this.redeemedDate = redeemedDate;
        this.redeemedStatus = redeemedStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getRedeemedDate() {
        return redeemedDate;
    }

    public String getFormattedRedeemedDate() {
        if (redeemedDate == null) return "No date";
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return df.format(redeemedDate);
    }

    public void setRedeemedDate(Timestamp redeemedDate) {
        this.redeemedDate = redeemedDate;
    }

    public String getRedeemedStatus() {
        return redeemedStatus;
    }

    public void setRedeemedStatus(String redeemedStatus) {
        this.redeemedStatus = redeemedStatus;
    }
}
