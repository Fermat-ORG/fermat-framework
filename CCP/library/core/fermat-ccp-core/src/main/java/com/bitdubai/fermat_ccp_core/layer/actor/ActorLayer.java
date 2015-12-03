package com.bitdubai.fermat_ccp_core.layer.actor;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_ccp_core.layer.actor.extra_wallet_user.ExtraWalletUserPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.actor.intra_user.IntraWalletUserPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorLayer extends AbstractLayer {

    public ActorLayer() {
        super(Layers.ACTOR);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new ExtraWalletUserPluginSubsystem());
            registerPlugin(new IntraWalletUserPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
