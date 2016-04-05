package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.interfaces;


import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions.CantGetSelectedActorException;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */


public interface ChatRemoteCommunitySelectableIdentity extends ActiveActorIdentityInformation {


    void select() throws CantGetSelectedActorException;

}
