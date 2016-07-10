package com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantDeleteIdentityException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantUpdateIdentityException;

import java.util.ArrayList;

/**
 * The interface <code>IntraWalletUserIdentityManager</code>
 * provides the methods to create and obtain intra users associated to a Device User.
 */
public interface IntraWalletUserIdentityManager  extends ModuleManager<FermatSettings, ActiveActorIdentityInformation> {

    /**
     * The method <code>getAllIntraWalletUsersFromCurrentDeviceUser</code> will give us a list of all the intra wallet users associated to the actual Device User logged in
     *
     * @return the list of intra wallet users associated to the current logged in Device User.
     *
     * @throws CantListIntraWalletUsersException if something goes wrong.
     */
    ArrayList<com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListIntraWalletUsersException;

    /**
     * The method <code>createNewIntraWalletUser</code> creates a new intra wallet user Identity for the logged in Device User and returns the
     * associated public key
     *
     * @param alias        the alias that the user choose as intra user identity
     * @param phrase        the alias that the user choose as intra user identity
     * @param profileImage the profile image to identify this identity
     *
     * @return the intra user created
     *
     * @throws com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException if something goes wrong.
     */

    IntraWalletUserIdentity createNewIntraWalletUser(String alias, String phrase, byte[] profileImage, Long accuracy, Frequency frequency,Location location) throws CantCreateNewIntraWalletUserException;

    /**
     *
     * @param alias
     * @param profileImage
     * @return
     * @throws com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException
     */

    IntraWalletUserIdentity createNewIntraWalletUser(String alias, byte[] profileImage, Long accuracy, Frequency frequency,Location location) throws CantCreateNewIntraWalletUserException;

    /**
     * The method <code>hasIntraUserIdentity</code> returns if has a intra user identity created
     *
     * @return
     * @throws CantListIntraWalletUsersException
     */

    boolean  hasIntraUserIdentity() throws CantListIntraWalletUsersException;

    /**
     * The method <code>updateIntraUserIdentity</code> change a identity information data
     * @param identityPublicKey
     * @param identityAlias
     * @param phrase
     * @param profileImage
     * @throws CantUpdateIdentityException
     */
    void  updateIntraUserIdentity(String identityPublicKey, String identityAlias, String phrase,byte[] profileImage, Long accuracy, Frequency frequency, Location location) throws CantUpdateIdentityException;

    /**
     *The method <code>deleteIntraUserIdentity</code> change identity status to inactive
     * @param identityPublicKey
     * @throws CantListIntraWalletUsersException
     */
    void  deleteIntraUserIdentity(String identityPublicKey) throws CantDeleteIdentityException;

    void registerIdentities();
}
