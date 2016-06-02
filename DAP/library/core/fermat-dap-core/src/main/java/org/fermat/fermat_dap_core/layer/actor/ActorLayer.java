package org.fermat.fermat_dap_core.layer.actor;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

import org.fermat.fermat_dap_core.layer.actor.asset_issuer.AssetIssuerPluginSubsystem;

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
            registerPlugin(new org.fermat.fermat_dap_core.layer.actor.asset_user.AssetUserPluginSubsystem());
            registerPlugin(new org.fermat.fermat_dap_core.layer.actor.redeem_point.RedeemPointPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "Actor Layer of DAP Platform",
                    "Problem trying to register a plugin."
            );
        }
    }

}
