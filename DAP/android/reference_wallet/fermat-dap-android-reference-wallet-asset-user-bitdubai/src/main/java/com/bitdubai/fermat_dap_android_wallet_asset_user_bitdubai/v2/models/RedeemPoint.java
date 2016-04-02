package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models;

import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.Address;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 3/3/16.
 */
public class RedeemPoint {
    private ActorAssetRedeemPoint actorAssetRedeemPoint;

    private byte[] image;
    private String name;
    private String address;

    public RedeemPoint(String name) {
        this.name = name;
    }

    public RedeemPoint(ActorAssetRedeemPoint actorAssetRedeemPoint) {
        this.actorAssetRedeemPoint = actorAssetRedeemPoint;
        setImage(actorAssetRedeemPoint.getProfileImage());
        setName(actorAssetRedeemPoint.getName());
        formatAddress(actorAssetRedeemPoint.getAddress());
    }

    private void formatAddress(Address add) {
        String addressStr = add.getCityName()
                + ", " + add.getCountryName()
                + ", " + add.getHouseNumber()
                + ", " + add.getPostalCode()
                + ", " + add.getProvinceName()
                + ", " + add.getStreetName();
        setAddress(addressStr);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
