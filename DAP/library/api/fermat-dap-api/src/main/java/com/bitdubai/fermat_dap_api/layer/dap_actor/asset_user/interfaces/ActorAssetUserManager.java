package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.ActorAssetUserGroupAlreadyExistException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDisconnectAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetUserManager extends FermatManager {

    /**
     * The method <code>getActorRegisteredByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey                    The public key of the Asset Actor User
     * @return                                  THe information associated with the actorPublicKey.
     * @throws CantGetAssetUserActorsException
     * @throws CantAssetUserActorNotFoundException
     */
    ActorAssetUser getActorByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;

    /**
     * The method <code>getActorRegisteredByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey                    The public key of the Asset Actor User
     * @return                                  THe information associated with the actorPublicKey.
     * @throws CantGetAssetUserActorsException
     * @throws CantAssetUserActorNotFoundException
     */
    ActorAssetUser getActorByPublicKey(String actorPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;
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
     * The method <code>registerActorInActorNetworkService</code> Register Actor in Actor Network Service
     */
    void registerActorInActorNetworkService() throws CantRegisterActorAssetUserException;

    /**
     * The method <code>createActorAssetUserRegisterInNetworkService</code> create Actor Registered
     *
     * @param actorAssetUsers                       Referred to the Identity publicKey
     * @throws CantCreateAssetUserActorException
     */
    void createActorAssetUserRegisterInNetworkService(List<ActorAssetUser> actorAssetUsers) throws CantCreateAssetUserActorException;

    void createActorAssetUserRegisterInNetworkService(ActorAssetUser actorAssetUsers) throws CantCreateAssetUserActorException;
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
     * The method <code>getAllAssetUserActorRegistered</code> get All Actors Registered in Actor Network Service
     * and used in Sub App Community, Filtering the NetworkType they belong
     *
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getAllAssetUserActorInTableRegistered(BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException;

    /**
     * The method <code>getAllAssetUserActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getAllAssetUserActorConnected(BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException;

    /**
     * The method <code>connectToActorAssetUser</code> Enable Connection
     * with Issuer (Requester) and Lists Users for get a CryptoAdress (Delivered)
     *
     * @throws CantConnectToActorAssetUserException
     */
    void connectToActorAssetUser(DAPActor requester, List<ActorAssetUser> actorAssetUsers, BlockchainNetworkType blockchainNetworkType) throws CantConnectToActorAssetUserException;

    /**
     * The method <code>connectToActorAssetUser</code> Enable Connection
     * with Issuer (Requester) and Lists Users for get a CryptoAdress (Delivered)
     *
     * @throws CantConnectToActorAssetUserException
     */
    void disconnectToActorAssetUser(ActorAssetUser user, BlockchainNetworkType blockchainNetworkType) throws CantDeleteRecordException, CantDisconnectAssetUserActorException;
    /**
     * The method <code>connectToActorAssetRedeemPoint</code> Enable Connection
     * with Requester and Deliver Redeem Point to Reddem Asset
     *
     * @throws CantConnectToActorAssetRedeemPointException
     */
    void connectToActorAssetRedeemPoint(ActorAssetUser requester, List<ActorAssetRedeemPoint> actorAssetRedeemPoint) throws CantConnectToActorAssetRedeemPointException;

    /**
     * The method <code>createAssetUserGroup</code> Register a group in database Actor Asset User
     * @param groupName
     * @throws CantCreateAssetUserGroupException
     */
    ActorAssetUserGroup createAssetUserGroup(String groupName) throws CantCreateAssetUserGroupException, ActorAssetUserGroupAlreadyExistException;

    /**
     * The method <code>updateAssetUserGroup</code> Update a group in database Actor Asset User
     * @param assetUserGroup
     * @throws CantUpdateAssetUserGroupException
     */
    void updateAssetUserGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException, RecordsNotFoundException;

    /**
     * The method <code>deleteAssetUserGroup</code> Delete a group in database Actor Asset User
     * @param assetUserGroupId
     * @throws CantDeleteAssetUserGroupException
     */
    void deleteAssetUserGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException, RecordsNotFoundException;

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
    void removeAssetUserFromGroup(ActorAssetUserGroupMember assetUserGroupMember) throws CantDeleteAssetUserGroupException, RecordsNotFoundException;

    /**
     * The method <code>getAssetUserGroupsList</code> Returns a list of groups
     * @return
     * @throws CantGetAssetUserGroupException
     */
    List<ActorAssetUserGroup> getAssetUserGroupsList() throws CantGetAssetUserGroupException;


    /**
     * The method <code>getListActorAssetUserByGroups</code> Returns a list of groups by name
     * @param groupId
     * @return
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getListActorAssetUserByGroups (String groupId, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException;

    /**
     * The method <code>getListAssetUserGroupsByActorAssetUser</code> Returns a list of groups by asset user
     * @param actorAssetUserPublicKey
     * @return
     * @throws CantGetAssetUserGroupException
     */
    List<ActorAssetUserGroup> getListAssetUserGroupsByActorAssetUser(String actorAssetUserPublicKey) throws CantGetAssetUserGroupException;

    /**
     * The method <code>getAssetUserGroup</code> Returns a group by id
     * @param groupId
     * @return
     * @throws CantGetAssetUserGroupException
     */
    ActorAssetUserGroup getAssetUserGroup(String groupId) throws CantGetAssetUserGroupException;
}
