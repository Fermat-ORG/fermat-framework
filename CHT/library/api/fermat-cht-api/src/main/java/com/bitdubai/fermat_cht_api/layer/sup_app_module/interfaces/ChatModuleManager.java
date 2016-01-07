package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;

/**
 * Created by franklin on 06/01/16.
 */
public interface ChatModuleManager extends ModuleManager<FermatSettings, ActiveActorIdentityInformation> {
    //TODO: Implementar y Documentar

    ChatManager getChatManager() throws CHTException;
}
