package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/15/16.
 */
public class FanIdentityManager implements TokenlyFanIdentityManager{

    @Override
    public List<Fan> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {
        return null;
    }

    @Override
    public Fan createFanIdentity(String alias, byte[] profileImage, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform) throws CantCreateFanIdentityException, FanIdentityAlreadyExistsException {
        return null;
    }

    @Override
    public void updateFanIdentity(String alias, UUID id, String publicKey, byte[] profileImage, String externalUserName, String externalAccessToken, ExternalPlatform externalPlatform) throws CantUpdateFanIdentityException {

    }

    @Override
    public Fan getFanIdentity(UUID publicKey) throws CantGetFanIdentityException, IdentityNotFoundException {
        return null;
    }
}
