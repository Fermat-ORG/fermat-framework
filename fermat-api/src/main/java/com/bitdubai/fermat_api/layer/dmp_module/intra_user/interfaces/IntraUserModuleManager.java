package com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantLoginIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantShowLoginIdentitiesException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantSolveRequestLaterException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantStartRequestException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CouldNotCreateIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantSaveProfileImageException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.IntraUserCancellingFailedException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.IntraUserConectionDenegationFailedException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.IntraUserDisconnectingFailedException;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager</code>
 * provides the methods for the Intra Users sub app.
 */
public interface IntraUserModuleManager {

    /**
     * The method <code>createIntraUser</code> is used to create a new intra user
     *
     * @param intraUserName the name of the intra user to create
     * @param profileImage  the profile image of the intra user to create
     * @return the login identity generated for the said intra user.
     * @throws CouldNotCreateIntraUserException
     */
    public IntraUserLoginIdentity createIntraUser(String intraUserName, byte[] profileImage) throws CouldNotCreateIntraUserException;

    /**
     * The method <code>setProfileImage</code> let the current logged in intra user set its profile
     * picture.
     * @param image the profile picture to set
     * @throws CantSaveProfileImageException
     */
    public void setNewProfileImage(byte[] image, String intraUserPublicKey) throws CantSaveProfileImageException;

    /**
     * The method <code>showAvailableLoginIdentities</code> lists the login identities that can be used
     * to log in as an Intra User for the current Device User.
     *
     * @return the list of identities the current Device User can use to log in
     * @throws CantShowLoginIdentitiesException
     */
    public List<IntraUserLoginIdentity> showAvailableLoginIdentities() throws CantShowLoginIdentitiesException;

    /**
     * The method <code>login</code> let an intra user log in
     *
     * @param intraUserPublicKey the public key of the intra user to log in
     */
    public void login(String intraUserPublicKey) throws CantLoginIntraUserException;

    /**
     * The method <code>getSuggestionsToContact</code> searches for intra users that the logged in
     * intra user could be interested to add.
     *
     * @return a list with information of intra users
     * @throws CantGetIntraUsersListException
     */
    public List<IntraUserInformation> getSuggestionsToContact(int max,int offset) throws CantGetIntraUsersListException;

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
     * @param intraUserToAddName      The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param profileImage            The profile image that the intra user has
     * @throws CantStartRequestException
     */
    public void askIntraUserForAcceptance(String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantStartRequestException;

    /**
     * The method <code>acceptIntraUser</code> takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserToAddName      The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param profileImage            The profile image that the intra user has
     * @throws CantAcceptRequestException
     */
    public void acceptIntraUser(String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantAcceptRequestException;


    /**
     * The method <code>denyConnection</code> denies a conection request from other intra user
     *
     * @param intraUserToRejectPublicKey the public key of the user to deny its connection request
     * @throws IntraUserConectionDenegationFailedException
     */
    public void denyConnection(String intraUserToRejectPublicKey) throws IntraUserConectionDenegationFailedException;

    /**
     * The method <code>disconnectIntraUSer</code> disconnect an intra user from the list managed by this
     * plugin
     *
     * @param intraUserToDisconnectPublicKey the public key of the intra user to disconnect
     * @throws IntraUserDisconnectingFailedException
     */
    public void disconnectIntraUSer(String intraUserToDisconnectPublicKey) throws IntraUserDisconnectingFailedException;

    /**
     * The method <code>cancelIntraUser</code> cancels an intra user from the list managed by this
     * @param intraUserToCancelPublicKey
     * @throws IntraUserCancellingFailedException
     */
    void cancelIntraUser(String intraUserToCancelPublicKey) throws IntraUserCancellingFailedException;

    /**
     * The method <code>getAllIntraUsers</code> returns the list of all intra users registered by the
     * logged in intra user
     *
     * @return the list of intra users connected to the logged in intra user
     * @throws CantGetIntraUsersListException
     */
    public List<IntraUserInformation> getAllIntraUsers(int max,int offset) throws CantGetIntraUsersListException;

    /**
     * The method <code>getIntraUsersWaitingYourAcceptance</code> returns the list of intra users waiting to be accepted
     * or rejected by the logged in intra user
     *
     * @return the list of intra users waiting to be accepted or rejected by the  logged in intra user
     * @throws CantGetIntraUsersListException
     */
    public List<IntraUserInformation> getIntraUsersWaitingYourAcceptance(int max,int offset) throws CantGetIntraUsersListException;

    /**
     * The method <code>getIntraUsersWaitingTheirAcceptance</code> list the intra users that haven't
     * answered to a sent connection request by the current logged in intra user.
     *
     * @return the list of intra users that haven't answered to a sent connection request by the current
     * logged in intra user.
     * @throws CantGetIntraUsersListException
     */
    public List<IntraUserInformation> getIntraUsersWaitingTheirAcceptance(int max,int offset) throws CantGetIntraUsersListException;

    /**
     *
     * @return active IntraUserLoginIdentity
     * @throws CantShowLoginIdentitiesException
     */
    public IntraUserLoginIdentity getActiveIntraUserIdentity() throws CantGetActiveLoginIdentityException;
}