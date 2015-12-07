package com.bitdubai.fermat_p2p_core;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_p2p_core.layer.communication.CommunicationsLayer;

/**
 * The class <code>com.bitdubai.fermat_p2p_core.P2PPlatform</code>
 * haves all the necessary business logic to start the P2P Communication platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/11/2015.
 */
public class P2PPlatform extends AbstractPlatform {

    public P2PPlatform() {
        super(new PlatformReference(Platforms.COMMUNICATION_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new CommunicationsLayer());

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}
