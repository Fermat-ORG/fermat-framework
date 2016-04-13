package org.fermat.fermat_dap_android_wallet_asset_issuer.models;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;

import java.util.List;

/**
 * Created by frank on 12/30/15.
 */
public class Group {
    private String name;
    private boolean selected;

    private ActorAssetUserGroup actorAssetUserGroup;

    private List<User> users;

    public Group() {
    }

    public Group(String name, ActorAssetUserGroup actorAssetUserGroup) {
        this.name = name;
        this.setActorAssetUserGroup(actorAssetUserGroup);
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

    public ActorAssetUserGroup getActorAssetUserGroup() {
        return actorAssetUserGroup;
    }

    public void setActorAssetUserGroup(ActorAssetUserGroup actorAssetUserGroup) {
        this.actorAssetUserGroup = actorAssetUserGroup;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        Group group = (Group) o;
        return this.getName().equals(group.getName());
    }
}
