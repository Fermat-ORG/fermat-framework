package com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetPersonalImageException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;

/**
 * <p>The class <code>com.bitdubai.fermat_pip_addon.layer.user.device_user.developer.bitdubai.version_1.structure.DeviceUserUser</code>
 * implements the interface DeviceUser with its methods and contains other attributes that complete the functionality of the device User.
 * <p/>
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 27/06/2015.
 *
 * @Version 1.0
 */
public class DeviceUserUser implements DeviceUser {

    String alias;

    String password;

    String privateKey;

    String publicKey;

    byte[] personalImage;

    public DeviceUserUser(String alias, String password, String privateKey, String publicKey, byte[] personalImage) {
        this.alias = alias;
        this.password = password;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.personalImage = personalImage;
    }

    public DeviceUserUser(String alias, String password, String privateKey, String publicKey) {
        this.alias = alias;
        this.password = password;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }


    @Override
    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public byte[] getPersonalImage() throws CantGetPersonalImageException {
        return personalImage;
    }
}
