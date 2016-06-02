package org.fermat.fermat_dap_api.layer.dap_actor.asset_user;

/**
 * Created by lcampo on 25/11/15.
 */
public class AssetUserGroupRecord implements org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup {

    private String groupId;
    private String groupName;


    public AssetUserGroupRecord() {
    }

    public AssetUserGroupRecord(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "AssetUserGroupRecord{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    /**
     * @param groupId
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
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
