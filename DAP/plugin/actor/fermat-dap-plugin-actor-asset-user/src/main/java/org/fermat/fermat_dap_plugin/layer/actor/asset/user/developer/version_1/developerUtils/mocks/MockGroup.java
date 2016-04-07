package org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.developerUtils.mocks;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;

/**
 * Created by lcampo on 30/11/15.
 */
public class MockGroup implements ActorAssetUserGroup {
    private String groupId;
    private String groupName;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String getGroupId() {
        return this.groupId;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
    }
}
