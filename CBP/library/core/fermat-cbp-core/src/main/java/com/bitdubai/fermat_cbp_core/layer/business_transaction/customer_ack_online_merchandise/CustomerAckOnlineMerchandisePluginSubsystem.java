package com.bitdubai.fermat_cbp_core.layer.business_transaction.customer_ack_online_merchandise;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_ack_online_merchandise.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 23/12/15.
 */
public class CustomerAckOnlineMerchandisePluginSubsystem extends AbstractPluginSubsystem {

    public CustomerAckOnlineMerchandisePluginSubsystem() {
        super(new PluginReference(Plugins.CUSTOMER_ACK_ONLINE_MERCHANDISE));
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