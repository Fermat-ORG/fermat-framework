package com.bitdubai.fermat_wpd_core.layer.engine;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_wpd_core.layer.engine.wallet_runtime.WalletRuntimePluginSubsystem;
import com.bitdubai.fermat_wpd_core.layer.identity.publisher.PublisherPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 12/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class EngineLayer extends AbstractLayer {

    public EngineLayer() {
        super(Layers.ENGINE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new WalletRuntimePluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "Engine Layer of WPD Platform.",
                    "Problem trying to register a plugin."
            );
        }
    }

}
