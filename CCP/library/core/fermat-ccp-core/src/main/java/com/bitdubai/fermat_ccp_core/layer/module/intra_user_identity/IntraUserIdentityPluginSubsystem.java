package com.bitdubai.fermat_ccp_core.layer.module.intra_user_identity;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user_identity.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by natalia on 05/01/16.
 */
public class IntraUserIdentityPluginSubsystem extends AbstractPluginSubsystem {

    public IntraUserIdentityPluginSubsystem() {
        super(new PluginReference(Plugins.INTRA_IDENTITY_USER));
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
