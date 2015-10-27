package com.bitdubai.fermat_osa_android_core.layer.android;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AndroidLayer extends AbstractLayer {

    public AndroidLayer() {
        super(Layers.ANDROID);
    }

    public void start() throws CantStartLayerException {

        /* register addons here */
        /*try {

            registerPlugin(null);


        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }*/
    }

}
