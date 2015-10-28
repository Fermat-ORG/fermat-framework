package com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces;

import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantAcceptIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantCancelIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantCreateIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantDenyConnectionException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantDisconnectIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantGetIntraWalletUsersException;

import java.util.List;

/**
 * The interface <code>IntraWalletUserManager</code>
 * defines the methods to administrate the intra users,
 */
public interface IntraWalletUserManager {

    /**
     * The method <code>askIntraWalletUserForAcceptance</code> registers a new intra user in the list
     * managed by this plugin with ContactState PENDING_REMOTELY_ACCEPTANCE until the other intra user
     * accepts the connection request sent also by this method.
     *
     * @param intraWalletUserIdentityToLinkPublicKey The public key of the intra user sending the connection request.
     * @param intraWalletUserToAddName               The name of the intra user to add
     * @param intraWalletUserToAddPublicKey          The public key of the intra user to add
     * @param profileImage                           The profile image that the intra user has
     *
     * @throws CantCreateIntraWalletUserException if something goes wrong.
     */
    void askIntraWalletUserForAcceptance(String intraWalletUserIdentityToLinkPublicKey,
                                         String intraWalletUserToAddName              ,
                                         String intraWalletUserToAddPublicKey         ,
                                         byte[] profileImage                          ) throws CantCreateIntraWalletUserException;


    /**
     * The method <code>acceptIntraWalletUser</code> takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the connection request.
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     * @throws CantAcceptIntraWalletUserException
     */
    void acceptIntraWalletUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) throws CantAcceptIntraWalletUserException;


    /**
     * The method <code>denyConnection</code> rejects a connection request from another intra user
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToRejectPublicKey The public key of the intra user that sent the request
     * @throws CantDenyConnectionException
     */
    void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey) throws CantDenyConnectionException;

    /**     
     * The method <code>disconnectIntraWalletUser</code> disconnect an intra user from the connections registry
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToDisconnectPublicKey The public key of the intra user to disconnect as connection
     * @throws CantDisconnectIntraWalletUserException
     */
    void disconnectIntraWalletUser(String intraUserLoggedInPublicKey, String intraUserToDisconnectPublicKey) throws CantDisconnectIntraWalletUserException;


    void receivingIntraWalletUserRequestConnection(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraWalletUserException;

    /**
     * The method <code>cancelIntraWalletUser</code> cancels an intra user from the connections registry
     * @param intraUserLoggedInPublicKey The public key of the intra user identity that is the receptor of the request
     * @param intraUserToCancelPublicKey The public key of the intra user to cancel as connection
     * @throws CantCancelIntraWalletUserException
     */
    void cancelIntraWalletUser(String intraUserLoggedInPublicKey, String intraUserToCancelPublicKey) throws CantCancelIntraWalletUserException;

    /**
     * The method <code>getAllIntraWalletUsers</code> shows the list of all intra users that are connections of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraWalletUsersException
     */
    List<IntraWalletUser> getAllIntraWalletUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraWalletUsersException;

    /**
     * The method <code>getAcceptedIntraWalletUsers</code> shows the list of all intra users that are accepted for the logged in one.
     *
     * @param intraUserLoggedInPublicKey
     * @return
     * @throws CantGetIntraWalletUsersException
     */
    List<IntraWalletUser> getConnectedIntraWalletUsers(String intraUserLoggedInPublicKey) throws CantGetIntraWalletUsersException;

    /**
     * The method <code>getWaitingYourAcceptanceIntraWalletUsers</code> shows the list of all intra users
     * that sent a connection request and are waiting for the acceptance of the logged in one.
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraWalletUsersException
     */
    List<IntraWalletUser> getWaitingYourAcceptanceIntraWalletUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraWalletUsersException;

    /**
     * The method <code>getWaitingTheirAcceptanceIntraWalletUsers</code> shows the list of all intra users
     * that the logged in one has sent connections request to and have not been answered yet..
     *
     * @param intraUserLoggedInPublicKey the public key of the intra user logged in
     * @return the list of intra users the logged in intra user has as connections.
     * @throws CantGetIntraWalletUsersException
     */
    List<IntraWalletUser> getWaitingTheirAcceptanceIntraWalletUsers(String intraUserLoggedInPublicKey, int max, int offset) throws CantGetIntraWalletUsersException;


}
