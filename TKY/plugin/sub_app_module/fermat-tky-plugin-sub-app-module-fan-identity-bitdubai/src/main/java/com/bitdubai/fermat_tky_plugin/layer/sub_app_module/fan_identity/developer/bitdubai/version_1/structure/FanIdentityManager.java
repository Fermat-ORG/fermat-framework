package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.layer.identity.Fan.interfaces.Fan;

import java.util.List;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/15/16.
 */
public class FanIdentityManager implements com.bitdubai.fermat_tky_api.layer.identity.Fan.interfaces.FanIdentityManager{
    @Override
    public List<Fan> listIdentitiesFromCurrentDeviceUser() {
        return null;
    }

    @Override
    public Fan createArtistIdentity(String alias, byte[] imageBytes) {
        return null;
    }

    @Override
    public void updateFanIdentity(String alias, String publicKey, byte[] imageProfile, String external) {

    }

    @Override
    public Fan getFanIdentity(String publicKey) {
        return null;
    }

    @Override
    public void publishIdentity(String publicKey) {

    }
}
