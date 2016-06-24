package org.fermat.fermat_dap_android_wallet_asset_user.models;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

import java.io.Serializable;

/**
 * Jinmy Bohorquez 15/02/2016.
 */
public class User implements Serializable {

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
        return this.getActorAssetUser().getActorPublicKey().equals(user.getActorAssetUser().getActorPublicKey());
    }
}
