package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.CantGetSelectedActorException;

/**
 * Created by Eleazar Oroño (eorono@protonmail.com) on 2/04/16.
 */
public interface ChatActorLocalCommunitySelectableIdentity extends ActiveActorIdentityInformation {

    void select() throws CantGetSelectedActorException;
}
