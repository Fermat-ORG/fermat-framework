package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity;

import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;

/**
 * Created by franklin on 04/04/16.
 */
//public interface ChatIdentitySupAppModuleManager extends ModuleManager<ChatPreferenceSettings, ActiveActorIdentityInformation>
public interface ChatIdentitySupAppModuleManager //extends ModuleManager<ChatIdentityPreferenceSettings, ActiveActorIdentityInformation>
{
    //TODO: Implementar y Documentar
    ChatIdentityModuleManager getChatIdentityManager() throws CHTException;
}
