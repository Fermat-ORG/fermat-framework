package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;

/**
 * Created by Luis Campo on 25/11/15.
 */
public class AssetUserGroupMemberRecord implements ActorAssetUserGroupMember {

    private String groupId;
    private String actorPublicKey;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setActorPublicKey(String actorPublicKey) {
        this.actorPublicKey = actorPublicKey;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getActorPublicKey() {
        return actorPublicKey;
    }
}
