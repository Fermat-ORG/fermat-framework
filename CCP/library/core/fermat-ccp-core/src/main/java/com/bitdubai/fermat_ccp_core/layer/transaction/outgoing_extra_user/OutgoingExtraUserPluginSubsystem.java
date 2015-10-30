package com.bitdubai.fermat_ccp_core.layer.transaction.outgoing_extra_user;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class OutgoingExtraUserPluginSubsystem extends AbstractPluginSubsystem {

    public OutgoingExtraUserPluginSubsystem() {
        super(new PluginReference(Plugins.OUTGOING_EXTRA_USER));
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