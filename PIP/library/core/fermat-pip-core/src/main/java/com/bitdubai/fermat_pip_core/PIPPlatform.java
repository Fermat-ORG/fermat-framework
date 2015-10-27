package com.bitdubai.fermat_pip_core;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_pip_core.layer.platform_service.PlatformServiceLayer;

/**
 * The class <code>com.bitdubai.fermat_pip_core.PIPPlatform</code>
 * haves all the necessary business logic to start the PIP platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 */
public class PIPPlatform extends AbstractPlatform {

    public PIPPlatform() {
        super(new PlatformReference(Platforms.PLUG_INS_PLATFORM));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new PlatformServiceLayer()         );

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}
