package org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;

import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.ActorAssetUserGroupAlreadyExistException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.exceptions.CantConnectToActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor.exceptions.CantDisconnectAssetActorException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCreateActorAssetReceiveException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

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
     * @throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException
     */
    ActorAssetUser getActorByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;

    /**
     * The method <code>getActorRegisteredByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey                    The public key of the Asset Actor User
     * @return                                  THe information associated with the actorPublicKey.
     * @throws CantGetAssetUserActorsException
     * @throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException
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

    void updateOfflineUserRegisterInNetworkService(List<ActorAssetUser> actorAssetUsers) throws CantGetAssetUserActorsException;

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
     * @throws CantConnectToActorAssetException
     */
    void connectToActorAssetUser(DAPActor requester, List<ActorAssetUser> actorAssetUsers, BlockchainNetworkType blockchainNetworkType) throws CantConnectToActorAssetException;

    /**
     * The method <code>connectToActorAssetRedeemPoint</code> Enable Connection
     * with Requester and Deliver Redeem Point to Reddem Asset
     *
     * @throws CantConnectToActorAssetRedeemPointException
     */
    void connectToActorAssetRedeemPoint(ActorAssetUser requester, List<ActorAssetRedeemPoint> actorAssetRedeemPoint, BlockchainNetworkType blockchainNetworkType) throws CantConnectToActorAssetRedeemPointException;

    /**
     * The method <code>createAssetUserGroup</code> Register a group in database Actor Asset User
     * @param groupName
     * @throws CantCreateAssetUserGroupException
     */
    ActorAssetUserGroup createAssetUserGroup(String groupName) throws CantCreateAssetUserGroupException, ActorAssetUserGroupAlreadyExistException;

    /**
     * The method <code>updateAssetUserGroup</code> Update a group in database Actor Asset User
     * @param assetUserGroup
     * @throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException
     */
    void updateAssetUserGroup(ActorAssetUserGroup assetUserGroup) throws CantUpdateAssetUserGroupException, RecordsNotFoundException;

    /**
     * The method <code>deleteAssetUserGroup</code> Delete a group in database Actor Asset User
     * @param assetUserGroupId
     * @throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDeleteAssetUserGroupException
     */
    void deleteAssetUserGroup(String assetUserGroupId) throws CantDeleteAssetUserGroupException, RecordsNotFoundException;

    /**
     * The method <code>addAssetUserToGroup</code> Add a user to a group
     * @param actorAssetUserGroupMember
     * @throws CantCreateAssetUserGroupException
     */
    void addAssetUserToGroup(ActorAssetUserGroupMember actorAssetUserGroupMember) throws CantCreateAssetUserGroupException;

    /**
     * The method <code>removeAssetUserFromGroup</code> Remove a user from group
     * @param assetUserGroupMember
     * @throws CantCreateAssetUserGroupException
     */
    void removeAssetUserFromGroup(ActorAssetUserGroupMember assetUserGroupMember) throws CantDeleteAssetUserGroupException, RecordsNotFoundException;

    /**
     * The method <code>getAssetUserGroupsList</code> Returns a list of groups
     * @return
     * @throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException
     */
    List<ActorAssetUserGroup> getAssetUserGroupsList() throws CantGetAssetUserGroupException;


    /**
     * The method <code>getListActorAssetUserByGroups</code> Returns a list of groups by name
     * @param groupId
     * @return
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getListActorAssetUserByGroups(String groupId, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException;

    /**
     * The method <code>getListAssetUserGroupsByActorAssetUser</code> Returns a list of groups by asset user
     * @param actorAssetUserPublicKey
     * @return
     * @throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException
     */
    List<ActorAssetUserGroup> getListAssetUserGroupsByActorAssetUser(String actorAssetUserPublicKey) throws CantGetAssetUserGroupException;

    /**
     * The method <code>getAssetUserGroup</code> Returns a group by id
     * @param groupId
     * @return
     * @throws org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserGroupException
     */
    ActorAssetUserGroup getAssetUserGroup(String groupId) throws CantGetAssetUserGroupException;

    DAPConnectionState getActorAssetUserRegisteredDAPConnectionState(String actorAssetPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetUserActorsException;

    /**
     * The method <code>askActorAssetUserForConnection</code> registers a new actor asset user in the list
     * managed by this plugin with ContactState PENDING_REMOTELY_ACCEPTANCE until the other intra user
     * accepts the connection request sent also by this method.
     *
     * @param actorAssetUserIdentityToLinkPublicKey The public key of the actor asset user sending the connection request.
     * @param actorAssetUserToAddName               The name of the actor asset user to add
     * @param actorAssetUserToAddPublicKey          The public key of the actor asset  user to add
     * @param profileImage                           The profile image that the actor asset user has
     *
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException if something goes wrong.
     */
    void askActorAssetUserForConnection(String actorAssetUserIdentityToLinkPublicKey,
                                        String actorAssetUserToAddName,
                                        String actorAssetUserToAddPublicKey,
                                        byte[] profileImage,
                                        BlockchainNetworkType blockchainNetworkType) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException;


    /**
     * The method <code>acceptIntraWalletUser</code> takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param actorAssetUserInPublicKey The public key of the intra user sending the connection request.
     * @param actorAssetUserToAddPublicKey    The public key of the intra user to add
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException
     */
    void acceptActorAssetUser(String actorAssetUserInPublicKey, String actorAssetUserToAddPublicKey) throws CantAcceptActorAssetUserException;


    /**
     * The method <code>denyConnection</code> rejects a connection request from another intra user
     *
     * @param actorAssetUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param actorAssetUserToRejectPublicKey The public key of the intra user that sent the request
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException
     */
    void denyConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, String actorAssetUserToRejectPublicKey) throws CantDenyConnectionActorAssetException;

    /**
     * The method <code>connectToActorAssetUser</code> Enable Connection
     * with Issuer (Requester) and Lists Users for get a CryptoAdress (Delivered)
     *
     * @throws CantConnectToActorAssetException
     */
    void disconnectToActorAssetUser(String actorAssetToDisconnect, BlockchainNetworkType blockchainNetworkType) throws CantDeleteRecordException, CantDisconnectAssetActorException;

//    /**
//     * The method <code>disconnectIntraWalletUser</code> disconnect an intra user from the connections registry
//     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
//     * @param intraUserToDisconnectPublicKey The public key of the intra user to disconnect as connection
//     * @throws CantDisconnectAssetUserActorException
//     */
//    void disconnectActorAssetUser(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws CantDisconnectAssetUserActorException;


    void receivingActorAssetUserRequestConnection(String actorAssetUserLoggedInPublicKey,
                                                  String actorAssetUserToAddName,
                                                  String actorAssetUserToAddPublicKey,
                                                  byte[] profileImage,
                                                  Actors actorsType) throws CantCreateActorAssetReceiveException;

    /**
     * The method <code>cancelIntraWalletUser</code> cancels an intra user from the connections registry
     * @param actorAssetUserToCancelPublicKey The public key of the intra user to cancel as connection
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException
     */
    void cancelActorAssetUser(String actorAssetUserToCancelPublicKey) throws CantCancelConnectionActorAssetException;

    /**
     * The method <code>getWaitingYourAcceptanceIntraWalletUsers</code> shows the list of all intra users
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param actorAssetUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException
     */
    List<DAPActor> getWaitingYourConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    /**
     * The method <code>getWaitingTheirConnectionActorAssetUser</code> shows the list of all actor asset users
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param actorAssetUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException
     */
    List<DAPActor> getWaitingTheirConnectionActorAssetUser(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    /**
     *The method <code>getLastNotificationActorAssetUser</code> get the last notification received by actor public key
     * @param actorAssetUserConnectedPublicKey
     * @return ActorAssetUser notification object
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException
     */
    ActorAssetUser getLastNotificationActorAssetUser(String actorAssetUserConnectedPublicKey) throws CantGetActorAssetNotificationException;
}
