package com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantAcceptIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDecideAcceptanceLaterException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDeleteIntraUserException;
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
     * The method <code>decideAcceptanceLater</code> adds a
     *
     * @param intraUserLoggedInPublicKey
     * @param intraUserToAddName
     * @param intraUserToAddPublicKey
     * @param profileImage
     * @throws CantDecideAcceptanceLaterException
     */
    void decideAcceptanceLater(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantDecideAcceptanceLaterException;

    void denyConnection(String intraUserLoggedInPublicKey, String intraUserToRejectPublicKey);

    void deleteIntraUSer(String intraUserLoggedInPublicKey, String intraUserToRemovePublicKey) throws CantDeleteIntraUserException;

    List<ActorIntraUser> getAllIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUSersException;

    List<ActorIntraUser> getWaitingYourAcceptanceIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUSersException;

    List<ActorIntraUser> getWaitingTheirAcceptanceIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUSersException;
    
}
