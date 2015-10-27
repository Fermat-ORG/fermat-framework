package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.CantConfirmNotificationException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorAcceptIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorAskIntraUserForAcceptanceException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorCancellingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorDenyConnectingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorDisconnectingIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorGetNotificationsIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorInIntraUserSearchException;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions.ErrorSearchingSuggestionsException;

import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 18/02/15.
 */
public interface IntraUserManager {

    /**
     * The method <code>searchIntraUserByName</code> searches for the intra users that matches the alias
     *
     * @param intraUserAlias the alias to search for
     * @return
     * @throws ErrorInIntraUserSearchException
     */
    public List<IntraUserInformation> searchIntraUserByName(String intraUserAlias) throws ErrorInIntraUserSearchException;

    /**
     * The method <code>getIntraUsersSuggestions</code> returns a list of intra users that the logged in
     * intra user may want to add as connections.
     *
     * @return The list of suggestions
     * @throws ErrorSearchingSuggestionsException
     */
    public List<IntraUserInformation> getIntraUsersSuggestions(int max, int offset) throws ErrorSearchingSuggestionsException;

    /**
     * The method <code>askIntraUserForAcceptance</code> sends a connection request to anothe intra user.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the request
     * @param intraUserToAddPublicKey    The public key of the intra user to send the request to
     * @param myProfileImage             The profile image of the user sending the request
     */
    void askIntraUserForAcceptance(String intraUserLoggedInPublicKey,String intraUserLoggedName,Actors senderType, String intraUserToAddName, String intraUserToAddPublicKey,Actors destinationType, byte[] myProfileImage) throws ErrorAskIntraUserForAcceptanceException;

    /**
     * The method <code>acceptIntraUser</code> send an acceptance message of a connection request.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user accepting the connection request.
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     */
    void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws ErrorAcceptIntraUserException;

    /**
     * The method <code>denyConnection</code> send an rejection message of a connection request.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user accepting the connection request.
     * @param intraUserToRejectPublicKey The public key of the intra user to add
     */
    void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws ErrorDenyConnectingIntraUserException;

    /**
     * The method <coda>disconnectIntraUSer</coda> disconnects and informs the other intra user the disconnecting
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user disconnecting the connection
     * @param intraUserToDisconnectPublicKey The public key of the user to disconnect
     * @throws ErrorDisconnectingIntraUserException
     */
    void disconnectIntraUSer(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws ErrorDisconnectingIntraUserException;

    /**
     * The method <coda>cancelIntraUSer</coda> cancels and informs the other intra user the cancelling
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user cancelling the connection
     * @param intraUserToCancelPublicKey The public key of the user to cancel
     * @throws ErrorCancellingIntraUserException
     */
    void cancelIntraUSer(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws ErrorCancellingIntraUserException;

    /**
     * The method <coda>getPendingNotifications</coda> returns all pending notifications
     * of responses to requests for connection
     *
     * @return List of IntraUserNotification
     */
    public List<IntraUserNotification> getPendingNotifications() throws ErrorGetNotificationsIntraUserException;


    /**
     *
     * @param intraUserLogedInPublicKey
     * @param intraUserInvolvedPublicKey
     */
//    public void confirmNotification(String intraUserLogedInPublicKey, String intraUserInvolvedPublicKey) throws CantConfirmNotificationException;

    /**
     *
     */
    public void confirmNotification(UUID notificationID) throws CantConfirmNotificationException;


    /**
     * Regist
     */
    public void registrateActors(List<Actor> actor);

    Actor contructIdentity(String publicKey, String alias,Actors actors ,byte[] profileImage);
}
