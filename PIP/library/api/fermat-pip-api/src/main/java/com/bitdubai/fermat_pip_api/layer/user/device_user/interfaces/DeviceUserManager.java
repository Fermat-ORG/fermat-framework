package com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetDeviceUserListException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantSetImageException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.IncorrectUserOrPasswordException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.LoginFailedException;

import java.util.List;

/**
 * Manager interface for Device User class.
 *
 * @author Ezequiel Postan
 */

public interface DeviceUserManager extends FermatManager {

    /**
     * This method creates internally a new device user and returns the public key assigned to it.
     *
     * @param alias    An string representing a user name chosen by the user
     * @param password A password for the corresponding alias
     * @return An string representing the Public Key of the user created
     * @throws com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantCreateNewDeviceUserException
     */
    String createNewDeviceUser(String alias, String password) throws com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantCreateNewDeviceUserException;

    /**
     * This method creates internally a new device user and returns the public key assigned to it.
     *
     * @param alias         An string representing a user name chosen by the user
     * @param password      A password for the corresponding alias
     * @param personalImage An image that the user will see in his login screen
     * @return An string representing the Public Key of the user created
     * @throws com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantCreateNewDeviceUserException
     */
    String createNewDeviceUser(String alias, String password, byte[] personalImage) throws com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantCreateNewDeviceUserException;

    /**
     * This method give us the list of all the device users registered in the device
     *
     * @return A list containing all the device users registered in the device
     * @throws CantGetDeviceUserListException
     */
    List<DeviceUser> getAllDeviceUsers() throws CantGetDeviceUserListException;

    /**
     * @param publicKey The public key that identifies the DeviceUser information we are looking for
     * @return The Device User information asked
     * @throws CantGetDeviceUserException report any problem to get the Device User information
     */
    DeviceUser getDeviceUser(String publicKey) throws CantGetDeviceUserException;

    /**
     * @return the actual user logged in
     */
    DeviceUser getLoggedInDeviceUser() throws CantGetLoggedInDeviceUserException;

    /**
     * This method check the password of the Device User assigned to the public key, set the logged in
     * user to the one specified and lunches an informative event
     *
     * @param publicKey The public key related to the Device User that tries to log in
     * @param password  The corresponding log in password
     * @throws IncorrectUserOrPasswordException An exception indicating that the parameters do not match an existing user information
     * @throws LoginFailedException             An exception indicating different failures
     */
    void login(String publicKey, String password) throws LoginFailedException, IncorrectUserOrPasswordException;

    /**
     * Logs out the current Device User logged in and lunch an informative event
     */
    void logout();

    /**
     * This method let the user set his personal image
     *
     * @param personalImage the image to ser
     */
    void setPersonalImage(byte[] personalImage) throws CantSetImageException;

}