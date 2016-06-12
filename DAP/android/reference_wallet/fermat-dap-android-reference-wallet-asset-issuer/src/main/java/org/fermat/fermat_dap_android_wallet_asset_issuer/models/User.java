package org.fermat.fermat_dap_android_wallet_asset_issuer.models;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.io.Serializable;

/**
 * Created by frank on 12/30/15.
 */
public class User implements Serializable {
    private String name;
    private byte[] image;
    private boolean selected;
    private boolean first;

    private ActorAssetUser actorAssetUser;

    public User() {
    }

    public User(String name, ActorAssetUser actorAssetUser) {
        this.name = name;
        this.image = actorAssetUser.getProfileImage();
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;
        return this.getName().equals(user.getName());
    }
}
