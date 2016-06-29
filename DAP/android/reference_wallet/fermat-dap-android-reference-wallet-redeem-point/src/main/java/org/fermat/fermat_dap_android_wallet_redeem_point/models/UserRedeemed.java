package org.fermat.fermat_dap_android_wallet_redeem_point.models;

import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Jinmy on 01/12/16.
 */
public class UserRedeemed implements Serializable {
    private String userName;
    private Timestamp redeemedDate;
    private String redeemedStatus;

    public UserRedeemed() {

    }

    public UserRedeemed(String userName, Timestamp redeemedDate) {
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
