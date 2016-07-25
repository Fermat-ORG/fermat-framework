package com.bitdubai.fermat_cbp_core.layer.actor_connection.crypto_broker;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_broker.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoBrokerPluginSubsystem extends AbstractPluginSubsystem {

    public CryptoBrokerPluginSubsystem() {
        super(new PluginReference(Plugins.CRYPTO_BROKER));
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
