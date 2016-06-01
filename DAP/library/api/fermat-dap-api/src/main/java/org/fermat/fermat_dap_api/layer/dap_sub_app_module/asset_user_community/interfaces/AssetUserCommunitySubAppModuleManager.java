package org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.ActorAssetUserGroupAlreadyExistException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroup;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserGroupMember;
import org.fermat.fermat_dap_api.layer.dap_actor.exceptions.CantConnectToActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor.exceptions.CantDisconnectAssetActorException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Nerio on 13/10/15.
 */

public interface AssetUserCommunitySubAppModuleManager extends ModuleManager, ModuleSettingsImpl<AssetUserSettings>, Serializable {

    DAPConnectionState getActorRegisteredDAPConnectionState(String actorAssetPublicKey) throws CantGetAssetUserActorsException;

    List<AssetUserActorRecord> getAllActorAssetUserRegistered() throws CantGetAssetUserActorsException;

    ActorAssetUser getActorUser(String actorAssetUserPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;

    List<ActorAssetUser> getAllActorAssetUserConnected() throws CantGetAssetUserActorsException;

    List<AssetUserActorRecord> getAllActorAssetUserRegisteredWithCryptoAddressNotIntheGroup(String groupId) throws CantGetAssetUserActorsException;

    void connectToActorAssetUser(DAPActor requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToActorAssetException;

//    List<ActorAssetRedeemPoint> getAllActorAssetRedeemPointRegistered() throws CantGetAssetRedeemPointActorsException;
    /**
     * The method <code>createGroup</code> Register a group in database Actor Asset User
     * @param groupName
     * @throws CantCreateAssetUserGroupException
     */
    ActorAssetUserGroup createAssetUserGroup(String groupName) throws CantCreateAssetUserGroupException, ActorAssetUserGroupAlreadyExistException;

    /**
     * The method <code>renameGroup</code> Update a group name in database Actor Asset User
     * @param assetUserGroup
     * @throws CantUpdateAssetUserGroupException
     */
    void renameGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException, RecordsNotFoundException;

    /**
     * The method <code>deleteGroup</code> Delete a group in database Actor Asset User
     * @param assetUserGroupId
     * @throws CantDeleteAssetUserGroupException
     */
    void deleteGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException, RecordsNotFoundException;

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
    void removeActorAssetUserFromGroup(ActorAssetUserGroupMember assetUserGroupMember) throws CantDeleteAssetUserGroupException, RecordsNotFoundException;

    /**
     * The method <code>getGroups</code> Returns a list of groups
     * @return
     * @throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException
     */
    List<ActorAssetUserGroup> getGroups() throws CantGetAssetUserGroupException;


    /**
     * The method <code>getListActorAssetUserByGroups</code> Returns a list of groups by name
     * @param groupId
     * @return
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getListActorAssetUserByGroups (String groupId) throws CantGetAssetUserActorsException;

    /**
     * The method <code>getListGroupsByActorAssetUser</code> Returns a list of groups by asset user
     * @param actorAssetUserPublicKey
     * @return
     * @throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException
     */
    List<ActorAssetUserGroup> getListGroupsByActorAssetUser(String actorAssetUserPublicKey) throws CantGetAssetUserGroupException;

    /**
     * The method <code>getGroup</code> Returns a group by id
     * @param groupId
     * @return
     * @throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException
     */
    ActorAssetUserGroup getGroup(String groupId) throws CantGetAssetUserGroupException;

    IdentityAssetUser getActiveAssetUserIdentity() throws CantGetIdentityAssetUserException;

    void askActorAssetUserForConnection(List<ActorAssetUser> actorAssetUsers) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException;

    void acceptActorAssetUser(String actorAssetUserInPublicKey, ActorAssetUser actorAssetToAdd) throws CantAcceptActorAssetUserException;

    void denyConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, ActorAssetUser actorAssetToReject) throws CantDenyConnectionActorAssetException;

    void disconnectToActorAssetUser(ActorAssetUser user) throws CantDisconnectAssetActorException;

//    void disconnectActorAssetUser(String intraUserLoggedInPublicKey, String actorAssetUserToDisconnectPublicKey) throws CantDisconnectAssetUserActorException;

    void cancelActorAssetUser(String actorAssetUserToCancelPublicKey) throws CantCancelConnectionActorAssetException;

    List<ActorAssetUser> getWaitingYourConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    List<ActorAssetUser> getWaitingTheirConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    ActorAssetUser getLastNotification(String actorAssetUserInPublicKey) throws CantGetActorAssetNotificationException;

    int getWaitingYourConnectionActorAssetUserCount();
}
