package com.bitdubai.fermat_wrd_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_wrd_core.layer.ApiLayer;

/**
 * This class has all the necessary business logic to start the WRD platform.
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public class WRDPlatform extends AbstractPlatform {

    public WRDPlatform() {
        super(new PlatformReference(Platforms.WORLD));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new ApiLayer() );

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer."
            );
        }
    }
}

