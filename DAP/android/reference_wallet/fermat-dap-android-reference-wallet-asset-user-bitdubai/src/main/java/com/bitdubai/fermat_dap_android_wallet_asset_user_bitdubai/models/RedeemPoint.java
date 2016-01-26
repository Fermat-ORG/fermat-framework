package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

/**
 * Created by frank on 12/30/15.
 */
public class RedeemPoint {
    private String name;
    private boolean selected;

    private ActorAssetRedeemPoint actorAssetRedeemPoint;

    public RedeemPoint() {
    }

    public RedeemPoint(String name, ActorAssetRedeemPoint actorAssetRedeemPoint) {
        this.name = name;
        this.setActorAssetRedeemPoint(actorAssetRedeemPoint);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
