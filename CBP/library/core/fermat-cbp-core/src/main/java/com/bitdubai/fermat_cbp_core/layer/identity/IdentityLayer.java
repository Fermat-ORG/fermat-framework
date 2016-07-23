package com.bitdubai.fermat_cbp_core.layer.identity;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.identity.crypto_broker.CryptoBrokerPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.identity.crypto_customer.CryptoCustomerPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class IdentityLayer extends AbstractLayer {

    public IdentityLayer() {
        super(Layers.IDENTITY);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new CryptoBrokerPluginSubsystem());
            registerPlugin(new CryptoCustomerPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
