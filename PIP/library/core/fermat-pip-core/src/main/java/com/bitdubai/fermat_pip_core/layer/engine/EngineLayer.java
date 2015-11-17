package com.bitdubai.fermat_pip_core.layer.engine;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_pip_core.layer.engine.desktop_runtime.DesktopRuntimePluginSubsystem;
import com.bitdubai.fermat_pip_core.layer.engine.sub_app_runtime.SubAppRuntimePluginSubsystem;

/**
 * The class <code>com.bitdubai.fermat_pip_core.layer.platform_service.EngineLayer</code>
 * contains all the necessary business logic to start the Engine Layer of the PIP Platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public class EngineLayer extends AbstractLayer {

    public EngineLayer() {
        super(Layers.ENGINE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new DesktopRuntimePluginSubsystem());
            registerPlugin(new SubAppRuntimePluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
