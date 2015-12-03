package com.bitdubai.fermat_bch_core.layer.middleware.crypto_addresses;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoAddressesPluginSubsystem extends AbstractPluginSubsystem {

    public CryptoAddressesPluginSubsystem() {
        super(new PluginReference(Plugins.CRYPTO_ADDRESSES));
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
