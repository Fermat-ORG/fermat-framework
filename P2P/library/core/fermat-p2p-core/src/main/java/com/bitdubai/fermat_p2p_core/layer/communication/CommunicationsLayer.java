package com.bitdubai.fermat_p2p_core.layer.communication;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_p2p_core.layer.communication.ws_cloud_client.WsCloudClientPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CommunicationsLayer extends AbstractLayer {

    public CommunicationsLayer() {
        super(Layers.COMMUNICATION);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new WsCloudClientPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
