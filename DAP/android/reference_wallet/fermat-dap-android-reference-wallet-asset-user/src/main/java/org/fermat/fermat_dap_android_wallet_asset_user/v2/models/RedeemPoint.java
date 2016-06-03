package org.fermat.fermat_dap_android_wallet_asset_user.v2.models;

import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.Address;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 3/3/16.
 */
public class RedeemPoint {
    private ActorAssetRedeemPoint actorAssetRedeemPoint;

    private byte[] image;
    private String name;
    private String address;
    private boolean selected;

    public RedeemPoint(String name) {
        this.name = name;
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

    public void setActorAssetRedeemPoint(ActorAssetRedeemPoint actorAssetRedeemPoint) {
        this.actorAssetRedeemPoint = actorAssetRedeemPoint;
    }

    public ActorAssetRedeemPoint getActorAssetRedeemPoint() {
        return actorAssetRedeemPoint;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
