package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;

import java.sql.Timestamp;

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
        return DAPStandardFormats.DATE_FORMAT.format(redeemedDate);
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
