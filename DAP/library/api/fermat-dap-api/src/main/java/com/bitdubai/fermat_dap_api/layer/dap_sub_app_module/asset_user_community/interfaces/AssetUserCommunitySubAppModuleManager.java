package com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupExcepcion;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;

import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */
public interface AssetUserCommunitySubAppModuleManager extends ModuleManager {

    List<AssetUserActorRecord> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException;

    void connectToActorAssetUser(ActorAssetIssuer requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToActorAssetUserException;

//    List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException;
    /**
     * The method <code>createGroup</code> Register a group in database Actor Asset User
     * @param assetUserGroup
     * @throws CantCreateAssetUserGroupException
     */
    void createGroup (ActorAssetUserGroup assetUserGroup) throws CantCreateAssetUserGroupException;

    /**
     * The method <code>renameGroup</code> Update a group name in database Actor Asset User
     * @param assetUserGroup
     * @throws CantUpdateAssetUserGroupException
     */
    void renameGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException;

    /**
     * The method <code>deleteGroup</code> Delete a group in database Actor Asset User
     * @param assetUserGroupId
     * @throws CantDeleteAssetUserGroupException
     */
    void deleteGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException;

    /**
     * The method <code>addActorAssetUserToGroup</code> Add a user to a group
     * @param actorAssetUserGroupMember
     * @throws CantCreateAssetUserGroupException
     */
    void addActorAssetUserToGroup (ActorAssetUserGroupMember actorAssetUserGroupMember) throws CantCreateAssetUserGroupException;

    /**
     * The method <code>removeActorAssetUserFromGroup</code> Remove a user from group
     * @param assetUserGroupMember
     * @throws CantCreateAssetUserGroupException
     */
    void removeActorAssetUserFromGroup (ActorAssetUserGroupMember assetUserGroupMember) throws CantCreateAssetUserGroupException;

    /**
     * The method <code>getGroups</code> Returns a list of groups
     * @return
     * @throws CantGetAssetUserGroupExcepcion
     */
    List<ActorAssetUserGroup>  getGroups() throws CantGetAssetUserGroupExcepcion;


    /**
     * The method <code>getListActorAssetUserByGroups</code> Returns a list of groups by name
     * @param groupName
     * @return
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getListActorAssetUserByGroups (String groupName) throws CantGetAssetUserActorsException;

    /**
     * The method <code>getListGroupsByActorAssetUser</code> Returns a list of groups by asset user
     * @param actorAssetUserPublicKey
     * @return
     * @throws CantGetAssetUserGroupExcepcion
     */
    List<ActorAssetUserGroup> getListGroupsByActorAssetUser (String actorAssetUserPublicKey) throws CantGetAssetUserGroupExcepcion;

    /**
     * The method <code>getGroup</code> Returns a group by id
     * @param groupId
     * @return
     * @throws CantGetAssetUserGroupExcepcion
     */
    ActorAssetUserGroup getGroup(String groupId) throws CantGetAssetUserGroupExcepcion;
}
