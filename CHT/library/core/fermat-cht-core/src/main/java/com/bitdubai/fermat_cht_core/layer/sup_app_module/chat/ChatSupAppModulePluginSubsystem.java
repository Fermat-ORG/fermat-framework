package com.bitdubai.fermat_cht_core.layer.sup_app_module.chat;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by franklin on 06/01/16.
 */
public class ChatSupAppModulePluginSubsystem extends AbstractPluginSubsystem {

    public ChatSupAppModulePluginSubsystem() {
        super(new PluginReference(Plugins.CHAT_SUP_APP_MODULE));
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
