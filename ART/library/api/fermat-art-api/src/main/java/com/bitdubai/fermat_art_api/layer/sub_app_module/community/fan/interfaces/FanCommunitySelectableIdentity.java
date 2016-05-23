package com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantSelectIdentityException;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public interface FanCommunitySelectableIdentity extends
        ActiveActorIdentityInformation,
        Serializable {

    /**
     * This method allows select an identity to work with.
     * @return the profile image of the Fan
     */
    void select() throws CantSelectIdentityException;
}
