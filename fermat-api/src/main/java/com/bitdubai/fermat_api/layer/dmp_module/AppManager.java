package com.bitdubai.fermat_api.layer.dmp_module;

import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;

/**
 * Created by Matias Furszyfer on 2016.03.04..
 */
public interface AppManager extends ModuleManager<DesktopManagerSettings, ActiveActorIdentityInformation> {

    //todo: hace falta agregar una excepcion para todos los que usan esto
    FermatApp getApp(String publicKey) throws Exception;

}
