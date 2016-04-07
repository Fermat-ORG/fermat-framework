package com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantSelectIdentityException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public interface FanCommunitySelectableIdentity extends ActiveActorIdentityInformation {

    /**
     * This method allows select an identity to work with.
     * @return the profile image of the crypto broker
     */
    void select() throws CantSelectIdentityException;
}
