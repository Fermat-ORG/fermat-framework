package com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/17/16.
 */
public interface TokenlyFanIdentityManagerModule {


    /**
     *
     * @param alias
     * @param profileImage
     * @param externalUserName
     * @param externalAccessToken
     * @param externalPlatform
     * @return
     * @throws CantCreateFanIdentityException
     * @throws FanIdentityAlreadyExistsException
     */
    Fan createFanIdentity(
            String alias, byte[] profileImage,
            String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform) throws
            CantCreateFanIdentityException,
            FanIdentityAlreadyExistsException;

    /**
     *
     * @param alias
     * @param id
     * @param publicKey
     * @param profileImage
     * @param externalUserName
     * @param externalAccessToken
     * @param externalPlatform
     * @throws CantUpdateFanIdentityException
     */
    void updateFanIdentity(
            String alias, UUID id,String publicKey, byte[] profileImage,
            String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform) throws
            CantUpdateFanIdentityException;

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


}
