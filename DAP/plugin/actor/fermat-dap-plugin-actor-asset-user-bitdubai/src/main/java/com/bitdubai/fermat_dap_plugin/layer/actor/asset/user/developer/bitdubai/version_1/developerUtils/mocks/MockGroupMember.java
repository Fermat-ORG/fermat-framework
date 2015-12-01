package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.developerUtils.mocks;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;

/**
 * Created by lcampo on 30/11/15.
 */
public class MockGroupMember implements ActorAssetUserGroupMember {

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
        return this.groupId;
    }

    @Override
    public String getActorPublicKey() {
        return this.actorPublicKey;
    }
}
