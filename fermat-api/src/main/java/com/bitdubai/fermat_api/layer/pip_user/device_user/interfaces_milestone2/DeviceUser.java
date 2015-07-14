package com.bitdubai.fermat_api.layer.pip_user.device_user.interfaces_milestone2;

/**
 * DeviceUser interface that represents the device user information.
 *
 * @author Ezequiel Postan
 */
public interface DeviceUser {
    /**
     *
     * @return the public key of the represented Device User
     */
    public String getPublicKey();

    /**
     *
     * @return the alias of the represented Device User
     */
    public String getAlias();
}
