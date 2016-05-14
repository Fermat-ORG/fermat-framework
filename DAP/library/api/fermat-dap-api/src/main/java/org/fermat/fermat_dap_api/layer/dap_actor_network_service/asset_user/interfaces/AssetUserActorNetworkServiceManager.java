package org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPNetworkService;

import java.util.List;
import java.util.UUID;

/**
 * Created by root on 07/10/15.
 */
public interface AssetUserActorNetworkServiceManager extends DAPNetworkService { //FermatManager {

    /**
     * Register the ActorAssetUser in the cloud server like online
     *
     * @param actorAssetUserToRegister
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException
     */
    void registerActorAssetUser(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser actorAssetUserToRegister) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;

    /**
     * Update the ActorAssetUser in the cloud server like online
     *
     * @param actorAssetUserToRegister
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException
     */
    void updateActorAssetUser(org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser actorAssetUserToRegister) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;

    /**
     * Get the list of the ActorAssetUser registered
     *
     * @return List<ActorAssetUser>
     */
    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser> getListActorAssetUserRegistered() throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;

    /**
     * Get the the ActorAssetUser registered by Public key
     *
     * @return List<ActorAssetUser>
     */
    List<org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser> getActorAssetUserRegistered(String actorAssetUserPublicKey) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;

    /**
     * The method <code>askConnectionActorAsset</code> sends a connection request to anothe intra user.
     *
     * @param actorAssetLoggedInPublicKey   The logged public key of the
     * @param actorAssetLoggedName          The logged name
     * @param senderType                    The senderType
     * @param actorAssetToAddPublicKey      The actorAssetToAddPublicKey
     * @param actorAssetToAddName           The actorAssetToAddName
     * @param destinationType               The public key of the
     * @param profileImage                  The profile image of the user sending the request
     */
    void askConnectionActorAsset(String actorAssetLoggedInPublicKey,
                                 String actorAssetLoggedName,
                                 Actors senderType,
                                 String actorAssetToAddPublicKey,
                                 String actorAssetToAddName,
                                 Actors destinationType,
                                 byte[] profileImage,
                                 BlockchainNetworkType blockchainNetworkType) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;

    /**
     * The method <code>acceptConnectionActorAsset</code> send an acceptance message of a connection request.
     *
     * @param actorAssetLoggedInPublicKey The public key of the actor asset accepting the connection request.
     * @param ActorAssetToAddPublicKey    The public key of the actor asset to add
     */
    void acceptConnectionActorAsset(String actorAssetLoggedInPublicKey, String ActorAssetToAddPublicKey) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptConnectionActorAssetException;

    /**
     * The method <code>denyConnectionActorAsset</code> send an rejection message of a connection request.
     *
     * @param actorAssetLoggedInPublicKey The public key of the actor asset accepting the connection request.
     * @param actorAssetToRejectPublicKey The public key of the actor asset to add
     */
    void denyConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToRejectPublicKey) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;

    /**
     * The method <coda>disconnectConnectionActorAsset</coda> disconnects and informs the other intra user the disconnecting
     *
     * @param actorAssetLoggedInPublicKey The public key of the actor asset disconnecting the connection
     * @param actorAssetToDisconnectPublicKey The public key of the user to disconnect
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDisconnectConnectionActorAssetException
     */
    void disconnectConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToDisconnectPublicKey) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDisconnectConnectionActorAssetException;

    /**
     * The method <coda>cancelConnectionActorAsset</coda> cancels and informs the other intra user the cancelling
     *
     * @param actorAssetLoggedInPublicKey The public key of the actor asset cancelling the connection
     * @param actorAssetToCancelPublicKey The public key of the user to cancel
     * @throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException
     */
    void cancelConnectionActorAsset(String actorAssetLoggedInPublicKey, String actorAssetToCancelPublicKey) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;

    /**
     * The method <coda>getPendingNotifications</coda> returns all pending notifications
     * of responses to requests for connection
     *
     * @return List of IntraUserNotification
     */
    List<org.fermat.fermat_dap_api.layer.dap_actor_network_service.interfaces.ActorNotification> getPendingNotifications() throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;

    /**
     * The method <coda>confirmActorAssetNotification</coda> confirm the pending notification
     */
    void confirmActorAssetNotification(UUID notificationID) throws org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantConfirmActorAssetNotificationException;

}
