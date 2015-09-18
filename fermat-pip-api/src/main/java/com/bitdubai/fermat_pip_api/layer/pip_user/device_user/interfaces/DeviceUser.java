package com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces;

import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetPersonalImageException;

/**
 * DeviceUser interface that represents the device user information.
 *
 * @author Ezequiel Postan
 */
public interface DeviceUser {

    /**
     * This image gives us the device user alias
     *
     * @return the alias of the represented Device User
     */
    String getAlias();

    /**
     * This method gives us the device user personal image
     *
     * @return the device user image
     */
    byte[] getPersonalImage() throws CantGetPersonalImageException;

    /**
     * This method returns the public key of the user
     *
     * @return the public key of the represented Device User
     */
    String getPublicKey();

}
