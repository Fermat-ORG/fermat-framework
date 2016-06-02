package org.fermat.fermat_dap_core.layer.network_service;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by lnacosta - (laion.cj91@gmail.com) on 11/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkServiceLayer extends AbstractLayer {

    public NetworkServiceLayer() {
        super(Layers.NETWORK_SERVICE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new org.fermat.fermat_dap_core.layer.network_service.asset_transmission.AssetTransmissionPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "NetworkService Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }

}
