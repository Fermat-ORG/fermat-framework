package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by frank on 12/30/15.
 */
public class User {
    private String name;
    private boolean selected;

    private ActorAssetUser actorAssetUser;

    public User() {
    }

    public User(String name, ActorAssetUser actorAssetUser) {
        this.name = name;
        this.setActorAssetUser(actorAssetUser);
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

    public ActorAssetUser getActorAssetUser() {
        return actorAssetUser;
    }

    public void setActorAssetUser(ActorAssetUser actorAssetUser) {
        this.actorAssetUser = actorAssetUser;
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;
        return this.getName().equals(user.getName());
    }
}
