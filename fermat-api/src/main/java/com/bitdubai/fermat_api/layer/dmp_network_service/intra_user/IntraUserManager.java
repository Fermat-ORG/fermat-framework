package com.bitdubai.fermat_api.layer.dmp_network_service.intra_user;

import java.util.List;

/**
 * Created by loui on 18/02/15.
 */
public interface IntraUserManager {

    /**
     * The method <code>askIntraUserForAcceptance</code> sends a connection request to anothe intra user.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user sending the request
     * @param intraUserLoggedInName      The name of the intra user sending the request
     * @param intraUserToAddPublicKey    The public key of the intra user to send the request to
     * @param myProfileImage             The profile image of the user sending the request
     */
    void askIntraUserForAcceptance(String intraUserLoggedInPublicKey, String intraUserLoggedInName, String intraUserToAddPublicKey, byte[] myProfileImage);

    /**
     * The method <code>acceptIntraUser</code> send an acceptance message of a connection request.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user accepting the connection request.
     * @param intraUserToAddPublicKey    The public key of the intra user to add
     */
    void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddPublicKey);

    /**
     * The method <code>denyConnection</code> send an rejection message of a connection request.
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user accepting the connection request.
     * @param intraUserToRejectPublicKey The public key of the intra user to add
     */
    void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey);

    /**
     * The method <coda>deleteIntraUSer</coda> deletes and informs the other intra user the deletion
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user deleting the connection
     * @param intraUserToRemovePublicKey The public key of the user to delete
     */
    void deleteIntraUSer(String intraUserLoggedInPublicKey, String intraUserToRemovePublicKey);

    List<IntraUser> getAllIntraUsers(String intraUserLoggedInPublicKey);

    List<IntraUser> getWaitingYourAcceptanceIntraUsers(String intraUserLoggedInPublicKey);

    List<IntraUser> getWaitingTheirAcceptanceIntraUsers(String intraUserLoggedInPublicKey);

}
