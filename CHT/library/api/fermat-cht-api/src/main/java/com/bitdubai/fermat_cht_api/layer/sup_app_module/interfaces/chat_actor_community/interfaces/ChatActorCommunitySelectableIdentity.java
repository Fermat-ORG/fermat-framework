package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantGetSelectedActorException;

import java.io.Serializable;

/**
 * Created by Eleazar Oroño (eorono@protonmail.com) on 2/04/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 23/06/16.
 */
public interface ChatActorCommunitySelectableIdentity extends ActiveActorIdentityInformation, Serializable {

    void select() throws CantGetSelectedActorException;

    byte[] getProfileImage();

    String getStatus();

    String getCountry();

    String getState();

    String getCity();

    String getConnectionState();

    long getAccuracy();

    GeoFrequency getFrequency();
}
