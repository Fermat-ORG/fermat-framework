package com.bitdubai.fermat_dap_core.layer.actor;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_dap_core.layer.actor.asset_issuer.AssetIssuerPluginSubsystem;
import com.bitdubai.fermat_dap_core.layer.actor.asset_user.AssetUserPluginSubsystem;
import com.bitdubai.fermat_dap_core.layer.actor.redeem_point.RedeemPointPluginSubsystem;

/**
 * Created by PatricioGesualdi - (pmgesualdi@hotmail.com) on 10/11/2015.
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

            registerPlugin(new AssetIssuerPluginSubsystem());
            registerPlugin(new AssetUserPluginSubsystem());
            registerPlugin(new RedeemPointPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "Actor Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }

}
