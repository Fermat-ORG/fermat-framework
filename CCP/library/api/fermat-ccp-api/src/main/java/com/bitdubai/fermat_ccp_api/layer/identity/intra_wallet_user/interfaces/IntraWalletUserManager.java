package com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces;

import com.bitdubai.fermat_api.layer.modules.ModuleManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantListIntraWalletUsersException;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUserManager</code>
 * provides the methods to create and obtain intra users associated to a Device User.
 */
public interface IntraWalletUserManager extends ModuleManager {

    /**
     * The method <code>getAllIntraWalletUsersFromCurrentDeviceUser</code> will give us a list of all the intra wallet users associated to the actual Device User logged in
     *
     * @return the list of intra wallet users associated to the current logged in Device User.
     *
     * @throws CantListIntraWalletUsersException if something goes wrong.
     */
    List<IntraWalletUser> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListIntraWalletUsersException;

    /**
     * The method <code>createNewIntraWalletUser</code> creates a new intra wallet user Identity for the logged in Device User and returns the
     * associated public key
     *
     * @param alias        the alias that the user choose as intra user identity
     * @param profileImage the profile image to identify this identity
     *
     * @return the intra user created
     *
     * @throws CantCreateNewIntraWalletUserException if something goes wrong.
     */
    IntraWalletUser createNewIntraWalletUser(String alias       ,
                                             byte[] profileImage) throws CantCreateNewIntraWalletUserException;


    /**
     * The method <code>hasIntraUserIdentity</code> returns if has a intra user identity created
     *
     * @return
     * @throws CantListIntraWalletUsersException
     */

    boolean  hasIntraUserIdentity() throws CantListIntraWalletUsersException;

}
