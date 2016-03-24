package com.bitdubai.fermat_art_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_art_api.all_definition.exceptions.CantPublishIdentityException;
import com.bitdubai.fermat_art_api.all_definition.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantCreateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_art_api.layer.identity.fan.exceptions.FanIdentityAlreadyExistsException;
import com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.Fan;

import java.util.List;

/**
 * Created by alexander on 3/15/16.
 */
public class FanIdentityManager implements com.bitdubai.fermat_art_api.layer.identity.fan.interfaces.FanIdentityManager {
    @Override
    public List<Fan> listIdentitiesFromCurrentDeviceUser() throws CantListFanIdentitiesException {
        return null;
    }

    @Override
    public Fan createArtistIdentity(String alias, byte[] imageBytes) throws CantCreateFanIdentityException, FanIdentityAlreadyExistsException {
        return null;
    }

    @Override
    public void updateFanIdentity(String alias, String publicKey, byte[] imageProfile, String external) throws CantUpdateFanIdentityException {

    }

    @Override
    public Fan getFanIdentity(String publicKey) throws CantGetFanIdentityException, IdentityNotFoundException {
        return null;
    }

    @Override
    public void publishIdentity(String publicKey) throws CantPublishIdentityException, IdentityNotFoundException {

    }
}
