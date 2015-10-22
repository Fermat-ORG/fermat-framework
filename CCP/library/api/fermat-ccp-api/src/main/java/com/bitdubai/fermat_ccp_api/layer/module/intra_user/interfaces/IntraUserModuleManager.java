package com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces;


import com.bitdubai.fermat_api.layer.modules.ModuleManager;

import java.util.List;

/**
 * The interface <code>IntraUserModuleManager</code>
 * provides the methods for the Intra Users sub app.
 */
public interface IntraUserModuleManager extends ModuleManager {


    /**
     * The method <code>createIntraUser</code> is used to create a new intra user
     *
     * @param intraUserName the name of the intra user to create
     * @param profileImage  the profile image of the intra user to create
     * @return the login identity generated for the said intra user.
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CouldNotCreateIntraUserException
     */
    public IntraUserLoginIdentity createIntraUser(String intraUserName, byte[] profileImage) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CouldNotCreateIntraUserException;

    /**
     * The method <code>setProfileImage</code> let the current logged in intra user set its profile
     * picture.
     * @param image the profile picture to set
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantSaveProfileImageException
     */
    public void setNewProfileImage(byte[] image, String intraUserPublicKey) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantSaveProfileImageException;

    /**
     * The method <code>showAvailableLoginIdentities</code> lists the login identities that can be used
     * to log in as an Intra User for the current Device User.
     *
     * @return the list of identities the current Device User can use to log in
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantShowLoginIdentitiesException
     */
    public List<IntraUserLoginIdentity> showAvailableLoginIdentities() throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantShowLoginIdentitiesException;

    /**
     * The method <code>login</code> let an intra user log in
     *
     * @param intraUserPublicKey the public key of the intra user to log in
     */
    public void login(String intraUserPublicKey) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantLoginIntraUserException;

    /**
     * The method <code>getSuggestionsToContact</code> searches for intra users that the logged in
     * intra user could be interested to add.
     *
     * @return a list with information of intra users
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException
     */
    public List<IntraUserInformation> getSuggestionsToContact(int max,int offset) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;

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
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantStartRequestException
     */
    public void askIntraUserForAcceptance(String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage,String identityPublicKey,String identityAlias) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantStartRequestException;

    /**
     * The method <code>acceptIntraUser</code> takes the information of a connection request, accepts
     * the request and adds the intra user to the list managed by this plugin with ContactState CONTACT.
     *
     * @param intraUserToAddName      The name of the intra user to add
     * @param intraUserToAddPublicKey The public key of the intra user to add
     * @param profileImage            The profile image that the intra user has
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException
     */
    public void acceptIntraUser(String identityPublicKey,String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException;


    /**
     * The method <code>denyConnection</code> denies a conection request from other intra user
     *
     * @param intraUserToRejectPublicKey the public key of the user to deny its connection request
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserConectionDenegationFailedException
     */
    public void denyConnection(String intraUserToRejectPublicKey) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserConectionDenegationFailedException;

    /**
     * The method <code>disconnectIntraUSer</code> disconnect an intra user from the list managed by this
     * plugin
     *
     * @param intraUserToDisconnectPublicKey the public key of the intra user to disconnect
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserDisconnectingFailedException
     */
    public void disconnectIntraUSer(String intraUserToDisconnectPublicKey) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserDisconnectingFailedException;

    /**
     * The method <code>cancelIntraUser</code> cancels an intra user from the list managed by this
     * @param intraUserToCancelPublicKey
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserCancellingFailedException
     */
    void cancelIntraUser(String intraUserToCancelPublicKey) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserCancellingFailedException;

    /**
     * The method <code>getAllIntraUsers</code> returns the list of all intra users registered by the
     * logged in intra user
     *
     * @return the list of intra users connected to the logged in intra user
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException
     */
    public List<IntraUserInformation> getAllIntraUsers(int max,int offset) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;

    /**
     * The method <code>getIntraUsersWaitingYourAcceptance</code> returns the list of intra users waiting to be accepted
     * or rejected by the logged in intra user
     *
     * @return the list of intra users waiting to be accepted or rejected by the  logged in intra user
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException
     */
    public List<IntraUserInformation> getIntraUsersWaitingYourAcceptance(String identityPublicKey,int max,int offset) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;

    /**
     * The method <code>getIntraUsersWaitingTheirAcceptance</code> list the intra users that haven't
     * answered to a sent connection request by the current logged in intra user.
     *
     * @return the list of intra users that haven't answered to a sent connection request by the current
     * logged in intra user.
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException
     */
    public List<IntraUserInformation> getIntraUsersWaitingTheirAcceptance(String identityPublicKey,int max,int offset) throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;

    /**
     *
     * @return active IntraUserLoginIdentity
     * @throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantShowLoginIdentitiesException
     */
    public IntraUserLoginIdentity getActiveIntraUserIdentity() throws com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;

    public int getIntraUsersWaitingYourAcceptanceCount();
}