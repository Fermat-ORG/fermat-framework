package org.fermat.fermat_dap_android_wallet_asset_issuer.models;

import org.fermat.fermat_dap_android_wallet_asset_issuer.util.Utils;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetStatistic;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by frank on 12/22/15.
 */
public class UserDelivery implements Serializable {

    private byte[] userImage;
    private String userName;
    private Timestamp deliveryDate;
    private String deliveryStatus;
    private String deliveryStatusDescription;

    AssetStatistic assetStatistic;

    public UserDelivery() {
    }

    public UserDelivery(AssetStatistic assetStatistic) {
        setUserImage(assetStatistic.getOwner().getProfileImage());
        setUserName(assetStatistic.getOwner().getName());
        setDeliveryDate(new Timestamp(assetStatistic.getDistributionDate().getTime()));
        setDeliveryStatus(assetStatistic.getStatus().getCode());
        setDeliveryStatusDescription(assetStatistic.getStatus().getDescription());
    }

    public UserDelivery(byte[] profileImage, String name, Timestamp timestamp, String deliveryStatus, String deliveryStatusDescription) {
        this(name, timestamp, deliveryStatus);
        this.deliveryStatusDescription = deliveryStatusDescription;
        this.userImage = profileImage;
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
        return Utils.getTimeAgo(deliveryDate.getTime());
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

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public String getDeliveryStatusDescription() {
        return deliveryStatusDescription;
    }

    public void setDeliveryStatusDescription(String deliveryStatusDescription) {
        this.deliveryStatusDescription = deliveryStatusDescription;
    }
}
