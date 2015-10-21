package com.bitdubai.fermat_ccp_core.layer.request;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartLayerException;
import com.bitdubai.fermat_ccp_core.layer.request.crypto_payment.CryptoPaymentPluginSubsystem;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CCPPlugins;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class RequestLayer extends AbstractLayer {

    public void start() throws CantStartLayerException {

        registerPlugin(
                CCPPlugins.BITDUBAI_CRYPTO_PAYMENT_REQUEST,
                new CryptoPaymentPluginSubsystem()
        );

    }

}
