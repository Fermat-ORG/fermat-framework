package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserGroupMemberRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserGroupRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupExcepcion;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetUserManager extends FermatManager {

    /**
     * The method <code>getActorByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey                    The public key of the Asset Actor User
     * @return                                  THe information associated with the actorPublicKey.
     * @throws CantGetAssetUserActorsException
     * @throws CantAssetUserActorNotFoundException
     */
    ActorAssetUser getActorByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;

    /**
     * The method <code>createActorAssetUserFactory</code> create Actor by a Identity
     *
     * @param assetUserActorPublicKey                       Referred to the Identity publicKey
     * @param assetUserActorName                            Referred to the Identity Alias
     * @param assetUserActorprofileImage                    Eeferred to the Identity profileImage
     * @throws CantCreateAssetUserActorException
     */
    void createActorAssetUserFactory(String assetUserActorPublicKey, String assetUserActorName, byte[] assetUserActorprofileImage) throws CantCreateAssetUserActorException;

    /**
     * The method <code>getActorPublicKey</code> get All Information about Actor
     *
     * @throws CantGetAssetUserActorsException
     */
    ActorAssetUser getActorAssetUser() throws CantGetAssetUserActorsException;

    /**
     * The method <code>getAllAssetUserActorRegistered</code> get All Actors Registered in Actor Network Service
     * and used in Sub App Community
     *
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getAllAssetUserActorInTableRegistered() throws CantGetAssetUserActorsException;

    /**
     * The method <code>getAllAssetUserActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException;

    /**
     * The method <code>connectToActorAssetUser</code> Stablish Connection
     * with Issuer (Requester) and Lists Users for get a CryptoAdress (Delivered)
     *
     * @throws CantConnectToAssetUserException
     */
    void connectToActorAssetUser(ActorAssetIssuer requester, List<ActorAssetUser> actorAssetUsers) throws CantConnectToAssetUserException;

    /**
     * The method <code>createAssetUserGroup</code> Register a group in database Actor Asset User
     * @param assetUserGroup
     * @throws CantCreateAssetUserGroupException
     */
    void createAssetUserGroup (ActorAssetUserGroup assetUserGroup) throws CantCreateAssetUserGroupException;

    /**
     * The method <code>updateAssetUserGroup</code> Update a group in database Actor Asset User
     * @param assetUserGroup
     * @throws CantUpdateAssetUserGroupException
     */
    void updateAssetUserGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException;

    /**
     * The method <code>deleteAssetUserGroup</code> Delete a group in database Actor Asset User
     * @param assetUserGroupId
     * @throws CantDeleteAssetUserGroupException
     */
    void deleteAssetUserGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException;

    /**
     * The method <code>addAssetUserToGroup</code> Add a user to a group
     * @param actorAssetUserGroupMember
     * @throws CantCreateAssetUserGroupException
     */
    void addAssetUserToGroup (ActorAssetUserGroupMember actorAssetUserGroupMember) throws CantCreateAssetUserGroupException;

    /**
     * The method <code>removeAssetUserFromGroup</code> Remove a user from group
     * @param assetUserGroupMember
     * @throws CantCreateAssetUserGroupException
     */
    void removeAssetUserFromGroup (ActorAssetUserGroupMember assetUserGroupMember) throws CantCreateAssetUserGroupException;

    /**
     * The method <code>getAssetUserGroupsList</code> Returns a list of groups
     * @return
     * @throws CantGetAssetUserGroupExcepcion
     */
    List<ActorAssetUserGroup>  getAssetUserGroupsList() throws CantGetAssetUserGroupExcepcion;


    /**
     * The method <code>getListActorAssetUserByGroups</code> Returns a list of groups by name
     * @param groupName
     * @return
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getListActorAssetUserByGroups (String groupName) throws CantGetAssetUserActorsException;

    /**
     * The method <code>getListAssetUserGroupsByActorAssetUser</code> Returns a list of groups by asset user
     * @param actorAssetUserPublicKey
     * @return
     * @throws CantGetAssetUserGroupExcepcion
     */
    List<ActorAssetUserGroup> getListAssetUserGroupsByActorAssetUser (String actorAssetUserPublicKey) throws CantGetAssetUserGroupExcepcion;

    /**
     * The method <code>getAssetUserGroup</code> Returns a group by id
     * @param groupId
     * @return
     * @throws CantGetAssetUserGroupExcepcion
     */
    ActorAssetUserGroup getAssetUserGroup(String groupId) throws CantGetAssetUserGroupExcepcion;
}
