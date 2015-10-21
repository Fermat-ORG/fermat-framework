package com.bitdubai.fermat_ccp_core.layer.actor;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_ccp_core.layer.actor.intra_wallet_user.IntraWalletUserSubsystem;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CCPPlugins;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActorLayer extends AbstractLayer {

    public void start() throws CantStartLayerException {

        addPlugin(
                CCPPlugins.BITDUBAI_INTRA_WALLET_USER_ACTOR,
                new IntraWalletUserSubsystem()
        );

    }

}
