package com.bitdubai.fermat_ccp_core.layer.module;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_ccp_core.layer.module.intra_wallet_user.IntraWalletUserPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ModuleLayer extends AbstractLayer {

    public ModuleLayer() {
        super(Layers.SUB_APP_MODULE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new IntraWalletUserPluginSubsystem());


        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
