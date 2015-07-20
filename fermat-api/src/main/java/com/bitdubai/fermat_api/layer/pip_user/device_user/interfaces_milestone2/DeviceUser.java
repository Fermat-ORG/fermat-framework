package com.bitdubai.fermat_api.layer.pip_user.device_user.interfaces_milestone2;

import com.bitdubai.fermat_api.layer.pip_user.device_user.exceptions_milestone2.CantGetPersonalImageException;

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
    public String getAlias();

    /**
     * This method gives us the device user personal image
     *
     * @return the device user image
     */
    public byte[] getPersonalImage() throws CantGetPersonalImageException;

    /**
     * This method returns the public key of the user
     *
     * @return the public key of the represented Device User
     */
    public String getPublicKey();
}
