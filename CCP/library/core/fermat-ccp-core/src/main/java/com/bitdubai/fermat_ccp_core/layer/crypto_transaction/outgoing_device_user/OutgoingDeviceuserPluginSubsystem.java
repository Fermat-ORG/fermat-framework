package com.bitdubai.fermat_ccp_core.layer.crypto_transaction.outgoing_device_user;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.outgoing_intra_actor.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by Joaquin Carrasquero on 22/03/16.
 */
public class OutgoingDeviceuserPluginSubsystem extends AbstractPluginSubsystem {

    public OutgoingDeviceuserPluginSubsystem() {
        super(new PluginReference(Plugins.BITDUBAI_OUTGOING_DEVICE_USER_TRANSACTION));
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
