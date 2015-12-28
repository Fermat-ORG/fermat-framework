package com.bitdubai.fermat_api.layer.modules.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;

/**
 * A module is an intermediate point between the graphic user interface and the fermat system.
 * In which module we will provide all the needs of a sub-app or a wallet.
 *
 * Created by Matias Furszyfer on 2015.10.18.
 * Updated by Leon Acosta on 12/21/2015.
 */
public interface ModuleManager<Z extends FermatSettings, Y extends ActiveActorIdentityInformation> extends FermatManager {

    /**
     * Through the method <code>getSettingsManager</code> we can get a settings manager for the specified
     * settings class parametrized.
     *
     * @return a new instance of the settings manager for the specified fermat settings object.
     */
    SettingsManager<Z> getSettingsManager();

    /**
     * Through the method <code>getSelectedActorIdentity</code> we can get the selected actor identity.
     *
     * @return an instance of the selected actor identity.
     *
     * @throws CantGetSelectedActorIdentityException if something goes wrong.
     */
    Y getSelectedActorIdentity() throws CantGetSelectedActorIdentityException;

}
