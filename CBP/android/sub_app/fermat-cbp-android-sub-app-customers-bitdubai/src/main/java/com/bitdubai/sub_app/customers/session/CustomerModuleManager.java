package com.bitdubai.sub_app.customers.session;

import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

/**
 * Created by mati on 2015.11.19..
 */
public class CustomerModuleManager implements ModuleManager{
    @Override
    public SettingsManager getSettingsManager() {
        return null;
    }

    @Override
    public ActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        return null;
    }
}
