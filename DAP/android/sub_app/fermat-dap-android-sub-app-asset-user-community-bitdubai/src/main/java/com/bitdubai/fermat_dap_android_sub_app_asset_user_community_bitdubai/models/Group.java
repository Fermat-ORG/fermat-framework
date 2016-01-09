package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserGroupRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;

/**
 * Created by Penny on 01/08/16.
 */
public class Group extends AssetUserGroupRecord
        implements ActorAssetUserGroup {

    public boolean selected;

    public Group() {
        super();
    }

    public Group(ActorAssetUserGroup record) {
        super(
                record.getGroupId(),
                record.getGroupName()
        );
    }
}
