package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        return df.format(deliveryDate);
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
