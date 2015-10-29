package com.bitdubai.fermat_ccp_core.layer.transaction;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_ccp_core.layer.transaction.incoming_extra_user.IncomingExtraUserPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.transaction.incoming_intra_user.IncomingIntraUserPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.transaction.outgoing_extra_user.OutgoingExtraUserPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.transaction.outgoing_intra_actor.OutgoingIntraActorPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TransactionLayer extends AbstractLayer {

    public TransactionLayer() {
        super(Layers.TRANSACTION);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new IncomingExtraUserPluginSubsystem());
            registerPlugin(new IncomingIntraUserPluginSubsystem());
            registerPlugin(new OutgoingExtraUserPluginSubsystem());
            registerPlugin(new OutgoingIntraActorPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
