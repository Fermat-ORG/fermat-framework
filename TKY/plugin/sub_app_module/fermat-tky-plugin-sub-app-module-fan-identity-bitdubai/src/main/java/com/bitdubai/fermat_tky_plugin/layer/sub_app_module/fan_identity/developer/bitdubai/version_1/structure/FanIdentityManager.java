package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.TokenlyFanIdentityManager;

import java.util.List;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/15/16.
 */
public class FanIdentityManager implements TokenlyFanIdentityManager{

    @Override
    public List<com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {
        return null;
    }

    @Override
    public com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan createArtistIdentity(String alias, byte[] imageBytes) throws CantCreateFanIdentityException, FanIdentityAlreadyExistsException {
        return null;
    }

    @Override
    public void updateFanIdentity(String alias, String publicKey, byte[] imageProfile, String external) throws CantUpdateFanIdentityException {

    }

    @Override
    public com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan getFanIdentity(String publicKey) throws CantGetFanIdentityException, IdentityNotFoundException {
        return null;
    }

    @Override
    public void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {

    }
}
