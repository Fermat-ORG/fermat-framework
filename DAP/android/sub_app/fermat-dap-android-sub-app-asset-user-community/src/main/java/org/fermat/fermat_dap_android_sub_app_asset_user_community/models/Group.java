package org.fermat.fermat_dap_android_sub_app_asset_user_community.models;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.AssetUserGroupRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;

/**
 * Created by Penny on 01/08/16.
 */
public class Group extends AssetUserGroupRecord
        implements ActorAssetUserGroup {

    public boolean selected;
    private int members;

    public Group() {
        super();
    }

    public Group(ActorAssetUserGroup record) {
        super(
                record.getGroupId(),
                record.getGroupName()
        );
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }
}
