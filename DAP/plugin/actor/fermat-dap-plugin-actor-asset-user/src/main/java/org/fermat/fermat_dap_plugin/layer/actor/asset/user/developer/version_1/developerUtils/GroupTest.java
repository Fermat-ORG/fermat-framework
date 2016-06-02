package org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.developerUtils;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.developerUtils.mocks.MockGroup;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.developerUtils.mocks.MockGroupMember;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcampo on 30/11/15.
 */
public class GroupTest {
    private static List<ActorAssetUserGroup> groupList;
    private static List<ActorAssetUserGroupMember> groupMemberList;

    public static List<ActorAssetUserGroup> getGroupList() {
        groupList = new ArrayList<ActorAssetUserGroup>();
        for (int i = 0; i < 10; i++) {
            MockGroup group = new MockGroup();
            group.setGroupId(i + "");
            group.setGroupName("Grupo " + i + 1);
            groupList.add(group);
        }
        return groupList;
    }

    public static List<ActorAssetUserGroupMember> getGroupMemberList() {
        groupMemberList = new ArrayList<ActorAssetUserGroupMember>();
        for (int i = 0; i < 3; i++) {
            MockGroupMember groupMember = new MockGroupMember();
            groupMember.setGroupId(groupList.get(0).getGroupId());
            groupMember.setActorPublicKey("publicKey " + i);
            groupMemberList.add(groupMember);
        }

        for (int i = 2; i < 6; i++) {
            MockGroupMember groupMember = new MockGroupMember();
            groupMember.setGroupId(groupList.get(1).getGroupId());
            groupMember.setActorPublicKey("publicKey " + i);
            groupMemberList.add(groupMember);
        }
        return groupMemberList;
    }

}
