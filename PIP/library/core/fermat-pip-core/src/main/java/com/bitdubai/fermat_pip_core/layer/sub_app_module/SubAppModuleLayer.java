package com.bitdubai.fermat_pip_core.layer.sub_app_module;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_pip_core.layer.sub_app_module.android_core.AndroidCorePluginSubsystem;
import com.bitdubai.fermat_pip_core.layer.sub_app_module.developer.DeveloperPluginSubsystem;

/**
 * The class <code>com.bitdubai.fermat_pip_core.layer.platform_service.SubAppModuleLayer</code>
 * contains all the necessary business logic to start the SubAppModule Layer of the PIP Platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public class SubAppModuleLayer extends AbstractLayer {

    public SubAppModuleLayer() {
        super(Layers.SUB_APP_MODULE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new DeveloperPluginSubsystem());
            registerPlugin(new AndroidCorePluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
