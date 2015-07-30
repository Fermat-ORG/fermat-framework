package com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantSolveRequestLaterException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantStartRequestException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.IntraUserConectionDenegationFailedException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.IntraUserDeletionFailedException;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModule</code>
 * provides the methods for the Intra Users sub app.
 */
public interface IntraUserModule {


    public void login(String intraUserPublicKey);
    /**
     * The method <code>getSuggestionsToContact</code> searches for intra users that the logged in
     * intra user could be interested to add.
     *
     * @return a list with information of intra users
     * @throws CantGetIntraUsersListException
     */
    public List<IntraUserInformation> getSuggestionsToContact() throws CantGetIntraUsersListException;

    /**
     * The method <code>searchIntraUser</code> gives us an interface to manage a search for a particular
     * intra user
     *
     * @return a searching interface
     */
    public IntraUserSearch searchIntraUser();

    /**
     * The method <code>askIntraUserForAcceptance</code> initialize the request of contact between
     * two intra users.
     *
     * @param intraUserToAddName The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param profileImage The profile image that the intra user has
     * @throws CantStartRequestException
     */
    public void askIntraUserForAcceptance(String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantStartRequestException;

    /**
     * The method <code>acceptIntraUser</code> takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserToAddName The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param profileImage The profile image that the intra user has
     * @throws CantAcceptRequestException
     */
    public void acceptIntraUser(String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantAcceptRequestException;

    /**
     * The method <code>decideAcceptanceLater</code> adds a
     *
     * @param intraUserToAddName
     * @param intraUserToAddPublicKey
     * @param profileImage
     * @throws CantSolveRequestLaterException
     */
    public void decideAcceptanceLater(String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantSolveRequestLaterException;

    public void denyConnection(String intraUserToPublicKey) throws IntraUserConectionDenegationFailedException;

    public void deleteIntraUSer(String intraUserToRemovePublicKey) throws IntraUserDeletionFailedException;

    public IntraUserInformation getAllIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUsersListException;

    public IntraUserInformation getWaitingYourAcceptanceIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUsersListException;

    public IntraUserInformation getWaitingTheirAcceptanceIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUsersListException;

}