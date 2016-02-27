package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantConnectToActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDisconnectAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantAssetRedeemPointActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantUpdateRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.RedeemPointNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantAskConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantCancelConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantDenyConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCreateActorAssetReceiveException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetWaitingException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions.CantRegisterActorAssetRedeemPointException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetRedeemPointManager extends FermatManager {

    /**
     * The method <code>getActorRegisteredByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey                    The public key of the Asset Actor Redeem Point
     * @return                                  The information associated with the actorPublicKey.
     * @throws CantGetAssetRedeemPointActorsException
     * @throws CantAssetRedeemPointActorNotFoundException
     */
    ActorAssetRedeemPoint getActorByPublicKey(String actorPublicKey) throws CantGetAssetRedeemPointActorsException, CantAssetRedeemPointActorNotFoundException;

    /**
     * The method <code>createActorAssetRedeemPointFactory</code> create Actor by a Identity
     *
     * @param assetRedeemPointActorPublicKey                Referred to the Identity publicKey
     * @param assetRedeemPointActorName                     Referred to the Identity Alias
     * @param assetRedeemPointActorprofileImage             Referred to the Identity profileImage
     * @throws CantCreateActorRedeemPointException
     */
    void createActorAssetRedeemPointFactory(String assetRedeemPointActorPublicKey, String assetRedeemPointActorName, byte[] assetRedeemPointActorprofileImage, String contactInformation, String countryName, String cityName) throws CantCreateActorRedeemPointException;

    /**
     * The method <code>registerActorInActorNetworkService</code> Register Actor in Actor Network Service
     */
    void registerActorInActorNetworkService() throws CantRegisterActorAssetRedeemPointException;

    /**
     * This method saves an already existing redeem point in the registered redeem point database,
     * usually uses when the redeem point request the issuer an extended public key, we save in
     * the issuer side this redeem point so we can retrieve its information on future uses.
     * @param redeemPoint The already existing redeem point with all its information
     * @throws CantCreateActorRedeemPointException
     */
    void saveRegisteredActorRedeemPoint(ActorAssetRedeemPoint redeemPoint) throws CantCreateActorRedeemPointException;

    /**
     * The method <code>createActorAssetRedeemPointRegisterInNetworkService</code> create Actor Registered
     *
     * @param actorAssetRedeemPoints                       Referred to the Identity publicKey
     * @throws CantCreateActorRedeemPointException
     */
    void createActorAssetRedeemPointRegisterInNetworkService(List<ActorAssetRedeemPoint> actorAssetRedeemPoints) throws CantCreateActorRedeemPointException;

    /**
     * The method <code>getActorAssetRedeemPoint</code> get All Information about Actor
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    ActorAssetRedeemPoint getActorAssetRedeemPoint() throws CantGetAssetRedeemPointActorsException;

    DAPConnectionState getActorRedeemPointRegisteredDAPConnectionState(String actorAssetPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetRedeemPointActorsException;
    /**
     * The method <code>getAllAssetUserActorRegistered</code> get All Actors Registered in Actor Network Service
     * and used in Sub App Community
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    List<ActorAssetRedeemPoint> getAllAssetRedeemPointActorInTableRegistered(BlockchainNetworkType blockchainNetworkType) throws CantGetAssetRedeemPointActorsException;

    /**
     * The method <code>getAllAssetIssuerActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    List<ActorAssetRedeemPoint> getAllRedeemPointActorConnected() throws CantGetAssetRedeemPointActorsException;

    List<ActorAssetRedeemPoint> getAllRedeemPointActorConnectedForIssuer(String issuerPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetAssetRedeemPointActorsException;

    /**
     * The method <code>sendMessage</code> Stablish Connection
     * with Requester and Lists Issuers Delivered
     *
     * @throws CantConnectToActorAssetUserException
     */
    void sendMessage(ActorAssetRedeemPoint requester, List<ActorAssetIssuer> actorAssetIssuers) throws CantConnectToActorAssetUserException;

    void updateRedeemPointDAPConnectionStateActorNetworkService(String actorPublicKey, DAPConnectionState state) throws CantUpdateRedeemPointException, RedeemPointNotFoundException;

    /**
     * The method <code>askActorAssetRedeemForConnection</code> registers a new actor asset user in the list
     * managed by this plugin with ContactState PENDING_REMOTELY_ACCEPTANCE until the other intra user
     * accepts the connection request sent also by this method.
     *
     * @param actorAssetUserIdentityToLinkPublicKey The public key of the actor asset user sending the connection request.
     * @param actorAssetUserToAddName               The name of the actor asset user to add
     * @param actorAssetUserToAddPublicKey          The public key of the actor asset  user to add
     * @param profileImage                           The profile image that the actor asset user has
     *
     * @throws CantAskConnectionActorAssetException if something goes wrong.
     */
    void askActorAssetRedeemForConnection(String actorAssetUserIdentityToLinkPublicKey,
                                        String actorAssetUserToAddName              ,
                                        String actorAssetUserToAddPublicKey         ,
                                        byte[] profileImage                         ,
                                        BlockchainNetworkType blockchainNetworkType ) throws CantAskConnectionActorAssetException, CantRequestAlreadySendActorAssetException;


    /**
     * The method <code>acceptActorAssetRedeem</code> takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param actorAssetUserInPublicKey The public key of the intra user sending the connection request.
     * @param actorAssetUserToAddPublicKey    The public key of the intra user to add
     * @throws CantAcceptActorAssetUserException
     */
    void acceptActorAssetRedeem(String actorAssetUserInPublicKey, String actorAssetUserToAddPublicKey) throws CantAcceptActorAssetUserException;

    /**
     * The method <code>denyConnectionActorAssetRedeem</code> rejects a connection request from another intra user
     *
     * @param actorAssetUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param actorAssetUserToRejectPublicKey The public key of the intra user that sent the request
     * @throws CantDenyConnectionActorAssetException
     */
    void denyConnectionActorAssetRedeem(String actorAssetUserLoggedInPublicKey, String actorAssetUserToRejectPublicKey) throws CantDenyConnectionActorAssetException;

    /**
     * The method <code>disconnectToActorAssetRedeemPoint</code> disconnect an intra user from the connections registry
     * @param actorUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param actorUserToDisconnectPublicKey The public key of the intra user to disconnect as connection
     * @throws CantDisconnectAssetUserActorException
     */
    void disconnectToActorAssetRedeemPoint(String actorUserLoggedInPublicKey, String actorUserToDisconnectPublicKey) throws CantDisconnectAssetUserActorException;


    void receivingActorAssetRedeemRequestConnection(String actorAssetUserLoggedInPublicKey,
                                                  String actorAssetUserToAddName,
                                                  String actorAssetUserToAddPublicKey,
                                                  byte[] profileImage) throws CantCreateActorAssetReceiveException;

    /**
     * The method <code>cancelActorAssetRedeem</code> cancels an intra user from the connections registry
     * @param actorAssetUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param actorAssetUserToCancelPublicKey The public key of the intra user to cancel as connection
     * @throws com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantCancelIntraWalletUserException
     */
    void cancelActorAssetRedeem(String actorAssetUserLoggedInPublicKey, String actorAssetUserToCancelPublicKey) throws CantCancelConnectionActorAssetException;

    /**
     * The method <code>getWaitingYourConnectionActorAssetRedeem</code> shows the list of all intra users
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param actorAssetUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetActorAssetWaitingException
     */
    List<ActorAssetRedeemPoint> getWaitingYourConnectionActorAssetRedeem(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    /**
     * The method <code>getWaitingTheirConnectionActorAssetRedeem</code> shows the list of all actor asset users
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param actorAssetUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetActorAssetWaitingException
     */
    List<ActorAssetRedeemPoint> getWaitingTheirConnectionActorAssetRedeem(String actorAssetUserLoggedInPublicKey, int max, int offset) throws CantGetActorAssetWaitingException;

    /**
     *The method <code>getLastNotificationActorAssetUser</code> get the last notification received by actor public key
     * @param actorAssetUserConnectedPublicKey
     * @return ActorAssetUser notification object
     * @throws CantGetActorAssetNotificationException
     */
    ActorAssetRedeemPoint getLastNotificationActorAssetRedeem(String actorAssetUserConnectedPublicKey) throws CantGetActorAssetNotificationException;

}
