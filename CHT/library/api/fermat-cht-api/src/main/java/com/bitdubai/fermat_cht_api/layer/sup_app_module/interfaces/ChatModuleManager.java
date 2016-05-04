package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;

/**
 * Created by franklin on 06/01/16.
 * Edited by Jose Cardozo josejcb (josejcb89@gmail.com) on 29/02/16.
 */
public interface ChatModuleManager extends ModuleManager<ChatPreferenceSettings, ActiveActorIdentityInformation> {
    //TODO: Implementar y Documentar

    ChatManager getChatManager() throws CHTException;
}
