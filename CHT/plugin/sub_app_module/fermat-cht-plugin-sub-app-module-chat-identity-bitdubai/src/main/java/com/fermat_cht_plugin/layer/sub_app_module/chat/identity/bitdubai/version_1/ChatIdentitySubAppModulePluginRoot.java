package com.fermat_cht_plugin.layer.sub_app_module.chat.identity.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityPreferenceSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 30/03/16.
 * Edited by Miguel Rincon on 18/04/2016
 */

@PluginInfo(createdBy = "Franklin Marcano", maintainerMail = "franklinmarcano1970@gmail.com", platform = Platforms.CHAT_PLATFORM, layer = Layers.SUB_APP_MODULE, plugin = Plugins.CHAT_IDENTITY_SUP_APP_MODULE)
public class ChatIdentitySubAppModulePluginRoot extends AbstractModule<ChatIdentityPreferenceSettings, ActiveActorIdentityInformation> {

    private ChatIdentityModuleManager chatIdentityModuleManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CHAT_IDENTITY)
    private ChatIdentityManager chatIdentityManager;

    public ChatIdentitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     *
     */
    @Override
    public void start(){
        /**
         *
         * Init the plugin manager
         *
         */
        System.out.println("******* Init Chat Sup App Module Identity ******");
        chatIdentityModuleManager = new com.fermat_cht_plugin.layer.sub_app_module.chat.identity.bitdubai.version_1.structure.ChatIdentitySupAppModuleManager(chatIdentityManager, pluginFileSystem, pluginId);
        //TODO: This method is only for testing, please, comment it when the test is finish, thanks.
        //testMethod("Franklin Marcano", new byte[0]);

    }

//    private void testMethod(String alias, byte[] profileImage)
//    {
//        try {
//            chatIdentityManager.createNewIdentityChat(alias, profileImage);
//        } catch (CantCreateNewChatIdentityException e) {
//            errorManager.reportUnexpectedPluginException(Plugins.CHAT_IDENTITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(e));
//        }
//    }

    @Override
    public ModuleManager<ChatIdentityPreferenceSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        if (chatIdentityModuleManager == null)
            chatIdentityModuleManager = new com.fermat_cht_plugin.layer.sub_app_module.chat.identity.bitdubai.version_1.structure.ChatIdentitySupAppModuleManager(chatIdentityManager, pluginFileSystem, pluginId);
        return chatIdentityModuleManager;
    }
}