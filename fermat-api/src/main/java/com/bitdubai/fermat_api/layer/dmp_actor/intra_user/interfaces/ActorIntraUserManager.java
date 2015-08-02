package com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantAcceptIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDecideAcceptanceLaterException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDeleteIntraUserException;
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
     * managed by this plugin with ContactState PENDING_HIS_ACCEPTANCE until the other intra user
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
     * The method <code>acceptIntraUser</code> takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddName         The name of the intra user to add
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @param profileImage               The profile image that the intra user has
     * @throws CantAcceptIntraUserException
     */
    void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantAcceptIntraUserException;

    /**
     * The method <code>decideAcceptanceLater</code> adds an intra user registry with state PENDING_YOUR_ACCEPTANCE
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddName         The name of the intra user to add
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @param profileImage               The profile image that the intra user has
     * @throws CantDecideAcceptanceLaterException
     */
    void decideAcceptanceLater(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantDecideAcceptanceLaterException;

    /**
     * The method <code>denyConnection</code> rejects a connection request from another intra user
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToRejectPublicKey The public key of the intra user that sent the request
     * @throws CantDenyConnectionException
     */
    void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws CantDenyConnectionException;

    /**
     * The method <code>deleteIntraUser</code> deletes an intra user from the connections registry
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToRemovePublicKey The public key of the intra user to delete as connection
     * @throws CantDeleteIntraUserException
     */
    void deleteIntraUser(String intraUserLoggedInPublicKey, String intraUserToRemovePublicKey) throws CantDeleteIntraUserException;

    /**
     * The method <code>getAllIntraUsers</code> shows the list of all intra users that are connections of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */
    List<ActorIntraUser> getAllIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUSersException;

    /**
     * The method <code>getWaitingYourAcceptanceIntraUsers</code> shows the list of all intra users
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */
    List<ActorIntraUser> getWaitingYourAcceptanceIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUSersException;

    /**
     * The method <code>getWaitingTheirAcceptanceIntraUsers</code> shows the list of all intra users
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraUSersException
     */
    List<ActorIntraUser> getWaitingTheirAcceptanceIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUSersException;
    
}
