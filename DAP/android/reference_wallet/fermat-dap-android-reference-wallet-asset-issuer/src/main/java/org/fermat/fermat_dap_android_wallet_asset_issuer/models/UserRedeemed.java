package org.fermat.fermat_dap_android_wallet_asset_issuer.models;

import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Penny on 01/08/16.
 */
public class UserRedeemed implements Serializable {
    private String userName;
    private Timestamp redeemedDate;
    private String redeemedStatus;
    private String redeemPoint;

    public UserRedeemed() {
    }

    public UserRedeemed(String userName, Timestamp redeemedDate, String redeemedStatus, String redeemPoint) {
        this.userName = userName;
        this.redeemedDate = redeemedDate;
        this.redeemedStatus = redeemedStatus;
        this.redeemPoint = redeemPoint;
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

    public String getRedeemPoint() {
        return redeemPoint;
    }

    public void setRedeemPoint(String redeemPoint) {
        this.redeemPoint = redeemPoint;
    }
}
