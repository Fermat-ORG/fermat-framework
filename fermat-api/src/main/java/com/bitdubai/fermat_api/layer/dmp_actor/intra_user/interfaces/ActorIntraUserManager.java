package com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantAcceptIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCancelIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDecideAcceptanceLaterException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDisconnectIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDenyConnectionException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantGetIntraUSersException;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager</code>
 * defines the methods to administrate the intra users,
 */
public interface ActorIntraUserManager {

    /**
     * The method <code>askIntraUserForAcceptance</code> registers a new intra user in the list
     * managed by this plugin with ContactState PENDING_REMOTELY_ACCEPTANCE until the other intra user
     * accepts the connection request sent also by this method.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddName         The name of the intra user to add
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @param profileImage               The profile image that the intra user has
     * @throws CantCreateIntraUserException
     */
    void askIntraUserForAcceptance(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraUserException;

    /**
     * * The method <code>receivingIntraUserRequestConnection</code> registers a new intra user in the list
     * managed by this plugin with ContactState PENDING_LOCALLY_ACCEPTANCE  until the other intra user
     * accepts the connection request sent also by this method.
     * @param intraUserLoggedInPublicKey
     * @param intraUserToAddName
     * @param intraUserToAddPublicKey
     * @param profileImage
     * @throws CantCreateIntraUserException
     */
  void receivingIntraUserRequestConnection(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraUserException ;


        /**
         * The method <code>acceptIntraUser</code> takes the information of a connection request, accepts
         * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
         *
         * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
         * @param intraUserToAddPublicKey    The public key of the intra user to add
         * @throws CantAcceptIntraUserException
         */
    void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws CantAcceptIntraUserException;


    /**
     * The method <code>denyConnection</code> rejects a connection request from another intra user
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToRejectPublicKey The public key of the intra user that sent the request
     * @throws CantDenyConnectionException
     */
    void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws CantDenyConnectionException;

    /**     
     * The method <code>disconnectIntraUser</code> disconnect an intra user from the connections registry
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToDisconnectPublicKey The public key of the intra user to disconnect as connection
     * @throws CantDisconnectIntraUserException
     */
    void disconnectIntraUser(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws CantDisconnectIntraUserException;

    /**
     * The method <code>cancelIntraUser</code> cancels an intra user from the connections registry
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToCancelPublicKey The public key of the intra user to cancel as connection
     * @throws CantCancelIntraUserException
     */
    void cancelIntraUser(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws CantCancelIntraUserException;

    /**
     * The method <code>getAllIntraUsers</code> shows the list of all intra users that are connections of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */
    List<ActorIntraUser> getAllIntraUsers(String intraUserLoggedInPublicKey,int max,int offset) throws CantGetIntraUSersException;

    /**
     * The method <code>getWaitingYourAcceptanceIntraUsers</code> shows the list of all intra users
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */
    List<ActorIntraUser> getWaitingYourAcceptanceIntraUsers(String intraUserLoggedInPublicKey,int max,int offset) throws CantGetIntraUSersException;

    /**
     * The method <code>getWaitingTheirAcceptanceIntraUsers</code> shows the list of all intra users
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */
    List<ActorIntraUser> getWaitingTheirAcceptanceIntraUsers(String intraUserLoggedInPublicKey,int max,int offset) throws CantGetIntraUSersException;
    
}
