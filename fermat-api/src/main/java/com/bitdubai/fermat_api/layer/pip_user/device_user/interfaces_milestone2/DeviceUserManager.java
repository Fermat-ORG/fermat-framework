package com.bitdubai.fermat_api.layer.pip_user.device_user.interfaces_milestone2;

import com.bitdubai.fermat_api.layer.pip_user.device_user.exceptions_milestone2.CabtGetDeviceUserListException;
import com.bitdubai.fermat_api.layer.pip_user.device_user.exceptions_milestone2.CantCreateNewDeviceUserException;
import com.bitdubai.fermat_api.layer.pip_user.device_user.exceptions_milestone2.CantGetDeviceUserException;
import com.bitdubai.fermat_api.layer.pip_user.device_user.exceptions_milestone2.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_api.layer.pip_user.device_user.exceptions_milestone2.CantLogOutException;
import com.bitdubai.fermat_api.layer.pip_user.device_user.exceptions_milestone2.LoginFailedException;
import com.bitdubai.fermat_api.layer.pip_user.device_user.exceptions_milestone2.IncorrectUserOrPasswordException;

import java.util.List;

/**
 * Manager interface for Device User class.
 *
 * @author Ezequiel Postan
 */

public interface DeviceUserManager {

    /**
     * This method creates internally a new device user and returns the public key assigned to it.
     *
     * @param alias An string representing a user name chosen by the user
     * @param password A password for the corresponding alias
     * @return An string representing the Public Key of the user created
     * @throws CantCreateNewDeviceUserException
     */
    public String createNewDeviceUser(String alias, String password) throws CantCreateNewDeviceUserException;

    /**
     * This method check the password of the Device User assigned to the public key, set the logged in
     * user to the one specified and lunches an informative event
     *
     * @param publicKey The public key related to the Device User that tries to log in
     * @param password The corresponding log in password
     * @throws IncorrectUserOrPasswordException An exception indicating that the parameters do not match an existing user information
     * @throws LoginFailedException An exception indicating different failures
     */
    public void login(String publicKey, String password) throws LoginFailedException, IncorrectUserOrPasswordException;

    /**
     *
     * @return the actual user logged in
     */
    public DeviceUser getLoggedInDeviceUser() throws CantGetLoggedInDeviceUserException;

    /**
     * Logs out the current Device User logged in and lunch an informative event
     * @throws CantLogOutException
     */
    public void logout() throws CantLogOutException;

    /**
     *
     * @param publicKey The public key that identifies the DeviceUser information we are looking for
     * @return The Device User information asked
     * @throws CantGetDeviceUserException report any problem to get the Device User information
     */
    public DeviceUser getDeviceUser(String publicKey) throws CantGetDeviceUserException;

    /**
     * This method give us the list of all the device users registered in the device
     * @return A list containing all the device users registered in the device
     * @throws CabtGetDeviceUserListException
     */
    public List<DeviceUser> getAllDeviceUsers() throws CabtGetDeviceUserListException;
}