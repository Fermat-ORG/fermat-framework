package com.bitdubai.fermat_ccp_core.layer.network_service.crypto_transmission;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CCPPlugins;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Matias Furszyfer - (matiasfurszyfer@gmail.com) on 09/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoTransmissionPluginSubsystem extends AbstractPluginSubsystem {

    public CryptoTransmissionPluginSubsystem() {
        super(new PluginReference(CCPPlugins.BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }

}