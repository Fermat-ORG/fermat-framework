package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;

import java.sql.Timestamp;

/**
 * Created by frank on 12/22/15.
 */
public class UserDelivery {
    private String userName;
    private Timestamp deliveryDate;
    private String deliveryStatus;

    public UserDelivery() {

    }

    public UserDelivery(String userName, Timestamp deliveryDate, String deliveryStatus) {
        this.userName = userName;
        this.deliveryDate = deliveryDate;
        this.deliveryStatus = deliveryStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public String getFormattedDeliveryDate() {
        if (deliveryDate == null) return "No date";
        return DAPStandardFormats.DATE_FORMAT.format(deliveryDate);
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
