package com.bitdubai.fermat_wpd_core.layer.identity.publisher;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
//import com.bitdubai.fermat_wpd_plugin.layer.identity.publisher.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 12/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PublisherPluginSubsystem extends AbstractPluginSubsystem {

    public PublisherPluginSubsystem() {
        super(new PluginReference(Plugins.PUBLISHER));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
//            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
