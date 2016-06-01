package com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public interface TokenlyFanIdentityManager extends  FermatManager, Serializable {


    /**
     * Through the method <code>listIdentitiesFromCurrentDeviceUser</code> we can get all the fan
     * identities linked to the current logged device user.
     * @return
     * @throws CantListFanIdentitiesException
     */
    List<Fan> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException;

    /**
     *
     * @param userName
     * @param profileImage
     * @param externalPassword
     * @param externalPlatform
     * @return
     * @throws CantCreateFanIdentityException
     * @throws FanIdentityAlreadyExistsException
     */
    Fan createFanIdentity(
            String userName, byte[] profileImage, String externalPassword, ExternalPlatform externalPlatform) throws
            CantCreateFanIdentityException,
            FanIdentityAlreadyExistsException, WrongTokenlyUserCredentialsException;

    /**
     *
     * @param userName
     * @param id
     * @param publicKey
     * @param profileImage
     * @param externalPlatform
     * @throws CantUpdateFanIdentityException
     */
    Fan updateFanIdentity(
            String userName, String password, UUID id, String publicKey, byte[] profileImage, ExternalPlatform externalPlatform) throws
            CantUpdateFanIdentityException, WrongTokenlyUserCredentialsException;

    /**
     * This method returns a Fan identity
     * @param publicKey
     * @return
     * @throws CantGetFanIdentityException
     * @throws IdentityNotFoundException
     */
    Fan getFanIdentity(UUID publicKey) throws
            CantGetFanIdentityException,
            IdentityNotFoundException;

    /**
     * This method updates a Fan identity in database.
     * This method can be used to update the plugin database when the Fan identity object include a
     * new artist connected to be persisted.
     * @param fan
     * @throws CantUpdateFanIdentityException
     */
    void updateFanIdentity(Fan fan) throws
            CantUpdateFanIdentityException;


}
