package com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraUserIdentitySettings;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantCreateNewIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantDeleteIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantGetIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantListIntraUsersIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.exceptions.CantUpdateIntraUserIdentityException;

import java.util.ArrayList;

/**
 * The interface <code>IntraUserIdentityModuleManager</code>
 * provides the methods for the Intra Users Identity sub app.
 */
/**
 * Created by natalia on 05/01/16.
 */
public interface IntraUserIdentityModuleManager extends ModuleManager<IntraUserIdentitySettings, ActiveActorIdentityInformation>, ModuleSettingsImpl<IntraUserIdentitySettings>{

    /**
     * The method <code>getAllIntraWalletUsersFromCurrentDeviceUser</code> will give us a list of all the intra wallet users associated to the actual Device User logged in
     *
     * @return the list of intra wallet users associated to the current logged in Device User.
     *
     * @throws CantListIntraUsersIdentityException if something goes wrong.
     */
    ArrayList<IntraUserModuleIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListIntraUsersIdentityException;

    IntraWalletUserIdentity getIntraWalletUsers() throws CantListIntraUsersIdentityException;
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
     * @throws CantCreateNewIntraUserIdentityException if something goes wrong.
     */
    IntraUserModuleIdentity createNewIntraWalletUser(String alias ,String phrase,
                                                     byte[] profileImage, long accuracy, Frequency frequency, Location location) throws CantCreateNewIntraUserIdentityException;


    /**
     *
     * @param alias
     * @param profileImage
     * @return
     * @throws CantCreateNewIntraUserIdentityException
     */
    IntraUserModuleIdentity createNewIntraWalletUser(String alias ,
                                                     byte[] profileImage, long accuracy, Frequency frequency, Location location) throws CantCreateNewIntraUserIdentityException;


    /**
     * The method <code>hasIntraUserIdentity</code> returns if has a intra user identity created
     *
     * @return
     * @throws CantGetIntraUserIdentityException
     */

    boolean  hasIntraUserIdentity() throws CantGetIntraUserIdentityException;

    /**
     * The method <code>updateIntraUserIdentity</code> change a identity information data
     * @param identityPublicKey
     * @param identityAlias
     * @param phrase
     * @param profileImage
     * @throws CantUpdateIntraUserIdentityException
     */
    void  updateIntraUserIdentity(String identityPublicKey, String identityAlias, String phrase,byte[] profileImage, long accuracy, Frequency frequency,Location location) throws CantUpdateIntraUserIdentityException;

    /**
     *The method <code>deleteIntraUserIdentity</code> change identity status to inactive
     * @param identityPublicKey
     * @throws CantDeleteIntraUserIdentityException
     */
    void  deleteIntraUserIdentity(String identityPublicKey) throws CantDeleteIntraUserIdentityException;

    Location getLocationManager() throws CantGetDeviceLocationException;



}