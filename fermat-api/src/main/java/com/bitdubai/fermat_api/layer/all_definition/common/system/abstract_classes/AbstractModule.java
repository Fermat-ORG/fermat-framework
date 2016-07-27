package com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

/**
 * Created by Matias Furszyfer on 2016.03.24..
 */
public abstract class AbstractModule<S extends FermatSettings, I extends ActiveActorIdentityInformation> extends AbstractPlugin {


    public AbstractModule(PluginVersionReference pluginVersionReference) {
        super(pluginVersionReference);
    }

    public abstract ModuleManager<S, I> getModuleManager() throws CantGetModuleManagerException;

}
