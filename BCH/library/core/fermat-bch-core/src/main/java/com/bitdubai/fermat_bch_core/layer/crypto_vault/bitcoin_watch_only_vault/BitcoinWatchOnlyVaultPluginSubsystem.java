package com.bitdubai.fermat_bch_core.layer.crypto_vault.bitcoin_watch_only_vault;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class BitcoinWatchOnlyVaultPluginSubsystem extends AbstractPluginSubsystem {

    public BitcoinWatchOnlyVaultPluginSubsystem() {
        super(new PluginReference(Plugins.BITCOIN_WATCH_ONLY_VAULT));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /*try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }*/
    }
}
