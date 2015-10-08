package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_dap_api.layer.dap_actor.DAPActor;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAcceptAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCancelAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDenyConnectionAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantDisconnectAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface ActorAssetUserManager {
    /**
     * The method <code>askAssetUserActorForAcceptance</code> registers a new Asset User Actor in the list
     * managed by this plugin with ContactState PENDING_REMOTELY_ACCEPTANCE until the other Asset User Actor
     * accepts the connection request sent also by this method.
     *
     * @param assetUserActorIdentityToLinkPublicKey The public key of the Asset User Actor sending the connection request.
     * @param assetUserActorToAddName               The name of the Asset User Actor to add
     * @param assetUserActorToAddPublicKey          The public key of the Asset User Actor to add
     * @param profileImage                          The profile image that the Asset User Actor has
     * @throws CantCreateAssetUserActorException if something goes wrong.
     */
    void askAssetUserActorForAcceptance(String assetUserActorIdentityToLinkPublicKey, String assetUserActorToAddName, String assetUserActorToAddPublicKey, byte[] profileImage) throws CantCreateAssetUserActorException;

    /**
     * The method <code>acceptAssetUserActor</code> takes the information of a connection request, accepts
     * the request and adds the Asset User Actor to the list managed by this plugin with ContactState CONTACT.
     *
     * @param assetUserActorLoggedInPublicKey The public key of the Asset User Actor sending the connection request.
     * @param assetUserActorToAddPublicKey    The public key of the Asset User Actor to add
     * @throws CantAcceptAssetUserActorException
     */
    void acceptAssetUserActor(String assetUserActorLoggedInPublicKey, String assetUserActorToAddPublicKey) throws CantAcceptAssetUserActorException;


    /**
     * The method <code>denyConnection</code> rejects a connection request from another Asset User Actor
     *
     * @param assetUserActorLoggedInPublicKey The public key of the Asset User Actor identity that is the receptor of the request
     * @param assetUserActorToRejectPublicKey The public key of the Asset User Actor that sent the request
     * @throws CantDenyConnectionAssetUserActorException
     */
    void denyConnection(String assetUserActorLoggedInPublicKey, String assetUserActorToRejectPublicKey) throws CantDenyConnectionAssetUserActorException;

    /**
     * The method <code>disconnectAssetUserActor</code> disconnect an Asset User Actor from the connections registry
     *
     * @param assetUserActorLoggedInPublicKey     The public key of the Asset User Actor identity that is the receptor of the request
     * @param assetUserActorToDisconnectPublicKey The public key of the Asset User Actor to disconnect as connection
     * @throws CantDisconnectAssetUserActorException
     */
    void disconnectAssetUserActor(String assetUserActorLoggedInPublicKey, String assetUserActorToDisconnectPublicKey) throws CantDisconnectAssetUserActorException;

    /**
     * The method <code>cancelAssetUserActor</code> cancels an Asset User Actor from the connections registry
     *
     * @param assetUserActorLoggedInPublicKey The public key of the Asset User Actor identity that is the receptor of the request
     * @param assetUserActorToCancelPublicKey The public key of the Asset User Actor to cancel as connection
     * @throws CantCancelAssetUserActorException
     */
    void cancelAssetUserActor(String assetUserActorLoggedInPublicKey, String assetUserActorToCancelPublicKey) throws CantCancelAssetUserActorException;

    /**
     * The method <code>getAllAssetUserActors</code> shows the list of all Asset User Actors that are connections of the logged in one.
     *
     * @param assetUserActorLoggedInPublicKey the public key of the Asset User Actor logged in
     * @return the list of Asset User Actors the logged in Asset User Actor has as connections.
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getAllAssetUserActors(String assetUserActorLoggedInPublicKey, int max, int offset) throws CantGetAssetUserActorsException;

   /**
     * The method <code>getWaitingYourAcceptanceAssetUserActors</code> shows the list of all Asset User Actors
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param assetUserActorLoggedInPublicKey the public key of the Asset User Actor logged in
     * @return the list of Asset User Actors the logged in Asset User Actor has as connections.
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getWaitingYourAcceptanceAssetUserActors(String assetUserActorLoggedInPublicKey, int max, int offset) throws CantGetAssetUserActorsException;

   /**
     * The method <code>getWaitingTheirAcceptanceAssetUserActors</code> shows the list of all Asset User Actors
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param assetUserActorLoggedInPublicKey the public key of the Asset User Actor logged in
     * @return the list of Asset User Actors the logged in Asset User Actor has as connections.
     * @throws CantGetAssetUserActorsException
     */
    List<ActorAssetUser> getWaitingTheirAcceptanceAssetUserActors(String assetUserActorLoggedInPublicKey, int max, int offset) throws CantGetAssetUserActorsException;

   /**
     * The method <code>receivingAssetUserActorRequestConnection</code> receives connection requests Asset User Actors
     *
     * @param assetUserActorLoggedInPublicKey the public key of the Asset User Actor logged in
     * @param assetUserActorToAddName               The name of the Asset User Actor to add
     * @param assetUserActorToAddPublicKey          The public key of the Asset User Actor to add
     * @param profileImage                          The profile image that the Asset User Actor has
     * @throws CantCreateAssetUserActorException
     */
    void receivingAssetUserActorRequestConnection(String assetUserActorLoggedInPublicKey, String assetUserActorToAddName, String assetUserActorToAddPublicKey, byte[] profileImage) throws CantCreateAssetUserActorException;

  /**
    * The method <code>getActorByPublicKey</code> shows the information associated with the actorPublicKey
    *
    * @param actorPublicKey the public key of the Asset Actor User
    * @return THe information associated with the actorPublicKey.
    * @throws CantGetAssetUserActorsException
    * @throws CantAssetUserActorNotFoundException
    */
    DAPActor getActorByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException;

}
