package com.bitdubai.fermat_tky_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_tky_core.layer.external_api.ExternalApiLayer;

/**
 * This class has all the necessary business logic to start the WRD platform.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public class TKYPlatform extends AbstractPlatform {

    public TKYPlatform() {
        super(new PlatformReference(Platforms.TOKENLY));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new ExternalApiLayer() );

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}

