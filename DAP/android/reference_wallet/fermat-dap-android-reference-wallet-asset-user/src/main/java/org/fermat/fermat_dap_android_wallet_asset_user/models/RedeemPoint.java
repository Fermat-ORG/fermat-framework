package org.fermat.fermat_dap_android_wallet_asset_user.models;

import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.Address;

import java.io.Serializable;

/**
 * Created by frank on 12/30/15.
 */
public class RedeemPoint implements Serializable {
    private String name;
    private byte[] image;
    private String address;
    private boolean selected;

    private ActorAssetRedeemPoint actorAssetRedeemPoint;

    public RedeemPoint() {
    }

    public RedeemPoint(String name, ActorAssetRedeemPoint actorAssetRedeemPoint) {
        this.name = name;
        this.setActorAssetRedeemPoint(actorAssetRedeemPoint);
    }

    public RedeemPoint(ActorAssetRedeemPoint actorAssetRedeemPoint) {
        if (actorAssetRedeemPoint.getProfileImage() != null) {
            setImage(actorAssetRedeemPoint.getProfileImage());
        }
        if (actorAssetRedeemPoint.getName() != null) {
            setName(actorAssetRedeemPoint.getName());
        }
        if (actorAssetRedeemPoint.getAddress() != null) {
            formatAddress(actorAssetRedeemPoint.getAddress());
        }
//        this.actorAssetRedeemPoint = actorAssetRedeemPoint;
        this.setActorAssetRedeemPoint(actorAssetRedeemPoint);

    }

    private void formatAddress(Address add) {
        String addressStr = null;
        if (!add.getCityName().isEmpty())
            addressStr = add.getCityName();
        if (!add.getCountryName().isEmpty())
            addressStr = addressStr + ", " + add.getCountryName();
        if (!add.getHouseNumber().isEmpty())
            addressStr = addressStr + ", " + add.getHouseNumber();
        if (!add.getPostalCode().isEmpty())
            addressStr = addressStr + ", " + add.getPostalCode();
        if (!add.getProvinceName().isEmpty())
            addressStr = addressStr + ", " + add.getProvinceName();
        if (!add.getStreetName().isEmpty())
            addressStr = addressStr + ", " + add.getStreetName();

/*                        String addressStr = add.getCityName()
                + ", " + add.getCountryName()
                + ", " + add.getHouseNumber()
                + ", " + add.getPostalCode()
                + ", " + add.getProvinceName()
                + ", " + add.getStreetName();
                */
        setAddress(addressStr);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ActorAssetRedeemPoint getActorAssetRedeemPoint() {
        return actorAssetRedeemPoint;
    }

    public void setActorAssetRedeemPoint(ActorAssetRedeemPoint actorAssetRedeemPoint) {
        this.actorAssetRedeemPoint = actorAssetRedeemPoint;
    }

    @Override
    public boolean equals(Object o) {
        RedeemPoint user = (RedeemPoint) o;
        return this.getName().equals(user.getName());
    }
}
