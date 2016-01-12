package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;

import java.sql.Timestamp;

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
        return DAPStandardFormats.DATE_FORMAT.format(appropiateDate);
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
