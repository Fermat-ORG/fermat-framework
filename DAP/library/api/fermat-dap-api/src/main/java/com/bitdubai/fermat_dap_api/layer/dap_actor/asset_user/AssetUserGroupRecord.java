package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;

/**
 * Created by lcampo on 25/11/15.
 */
public class AssetUserGroupRecord implements ActorAssetUserGroup {

    private String groupId;
    private String groupName;

    /**
     *
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     *
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getGroupName() {
        return groupName;
    }
}
