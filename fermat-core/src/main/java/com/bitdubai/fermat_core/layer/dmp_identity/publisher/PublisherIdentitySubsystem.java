package com.bitdubai.fermat_core.layer.dmp_identity.publisher;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.dmp_identity.IdentitySubsystem;
import com.bitdubai.fermat_api.layer.dmp_identity.CantStartSubsystemException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.publisher.publisher.bitdubai.DeveloperBitDubai;

/**
 * Created by Nerio on 13/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PublisherIdentitySubsystem implements IdentitySubsystem {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai publisherBitDubai = new DeveloperBitDubai();
            plugin = publisherBitDubai.getPlugin();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }
}
