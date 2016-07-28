package com.bitdubai.fermat_cbp_core.layer.actor_network_service.crypto_customer;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoCustomerPluginSubsystem extends AbstractPluginSubsystem {

    public CryptoCustomerPluginSubsystem() {
        super(new PluginReference(Plugins.CRYPTO_CUSTOMER));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("Exception: ").append(e.getMessage()).toString());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
