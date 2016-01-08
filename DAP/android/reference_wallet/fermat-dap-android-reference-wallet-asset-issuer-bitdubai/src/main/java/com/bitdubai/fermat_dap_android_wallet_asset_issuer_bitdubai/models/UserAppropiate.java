package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Penny on 01/08/16.
 */
public class UserAppropiate {
    private String userName;
    private Timestamp appropiateDate;
    private String appropiateStatus;

    public UserAppropiate() {

    }

    public UserAppropiate(String userName, Timestamp appropiateDate, String appropiateStatus) {
        this.userName = userName;
        this.appropiateDate = appropiateDate;
        this.appropiateStatus = appropiateStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getAppropiateDate() {
        return appropiateDate;
    }

    public String getFormattedAppropiateDate() {
        if (appropiateDate == null) return "No date";
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return df.format(appropiateDate);
    }

    public void setAppropiateDate(Timestamp appropiateDate) {
        this.appropiateDate = appropiateDate;
    }

    public String getAppropiateStatus() {
        return appropiateStatus;
    }

    public void setAppropiateStatus(String appropiateStatus) {
        this.appropiateStatus = appropiateStatus;
    }
}
