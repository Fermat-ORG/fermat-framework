package com.bitdubai.fermat_cbp_core.layer.sub_app_module;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.sub_app_module.crypto_broker_identity.CryptoBrokerIdentityPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.sub_app_module.crypto_customer_identity.CryptoCustomerIdentityPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class SubAppModuleLayer extends AbstractLayer {

    public SubAppModuleLayer() {
        super(Layers.SUB_APP_MODULE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new CryptoBrokerIdentityPluginSubsystem());
            registerPlugin(new CryptoCustomerIdentityPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
