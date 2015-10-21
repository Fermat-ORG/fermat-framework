package com.bitdubai.fermat_ccp_core.layer.basic_wallet;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CCPPlugins;
import com.bitdubai.fermat_ccp_core.layer.actor.intra_wallet_user.IntraWalletUserSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BasicWalletLayer extends AbstractLayer {

    public void start() throws CantStartLayerException {

        addPlugin(
                CCPPlugins.BITDUBAI_INTRA_WALLET_USER_ACTOR,
                new IntraWalletUserSubsystem()
        );

    }

}
