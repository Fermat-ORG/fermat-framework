package com.bitdubai.fermat_osa_android_core;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlatform;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterLayerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartPlatformException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PlatformReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_osa_android_core.layer.android.AndroidLayer;

/**
 * The class <code>com.bitdubai.fermat_osa_android_core.OSAPlatform</code>
 * haves all the necessary business logic to start the OSA platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 */
public class OSAPlatform extends AbstractPlatform {

    public OSAPlatform() {
        super(new PlatformReference(Platforms.OPERATIVE_SYSTEM_API));
    }

    @Override
    public void start() throws CantStartPlatformException {

        try {

            registerLayer(new AndroidLayer());

        } catch (CantRegisterLayerException e) {

            throw new CantStartPlatformException(
                    e,
                    "",
                    "Problem trying to register a layer for OSA Platform."
            );
        }
    }
}
