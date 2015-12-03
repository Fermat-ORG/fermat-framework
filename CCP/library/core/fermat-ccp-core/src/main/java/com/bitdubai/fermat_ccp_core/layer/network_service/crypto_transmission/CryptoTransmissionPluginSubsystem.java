package com.bitdubai.fermat_ccp_core.layer.network_service.crypto_transmission;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 09/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoTransmissionPluginSubsystem extends AbstractPluginSubsystem {

    public CryptoTransmissionPluginSubsystem() {
        super(new PluginReference(Plugins.CRYPTO_TRANSMISSION));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}