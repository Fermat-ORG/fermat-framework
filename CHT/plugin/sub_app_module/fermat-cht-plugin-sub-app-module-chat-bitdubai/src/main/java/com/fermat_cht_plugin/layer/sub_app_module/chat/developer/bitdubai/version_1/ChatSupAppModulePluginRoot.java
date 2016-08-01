package com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.MiddlewareChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.fermat_cht_plugin.layer.sub_app_module.chat.developer.bitdubai.version_1.structure.ChatSupAppModuleManager;


/**
 * Created by franklin on 06/01/16.
 * Edited by Jose Cardozo josejcb (josejcb89@gmail.com) on 29/02/16.
 */
@PluginInfo(createdBy = "Franklin Marcano", maintainerMail = "franklinmarcano1970@gmail.com", platform = Platforms.CHAT_PLATFORM, layer = Layers.SUB_APP_MODULE, plugin = Plugins.CHAT_SUP_APP_MODULE)
public class ChatSupAppModulePluginRoot extends AbstractModule<ChatPreferenceSettings, ActiveActorIdentityInformation> {

    private ChatManager chatManager;

    public ChatSupAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.CHAT_MIDDLEWARE)
    private MiddlewareChatManager chatMiddlewareManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CHAT_IDENTITY)
    private ChatIdentityManager chatIdentityManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.ACTOR_CONNECTION, plugin = Plugins.CHAT_ACTOR_CONNECTION)
    private ChatActorConnectionManager chatActorConnectionManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CHAT_ACTOR_NETWORK_SERVICE)
    private com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager chatActorNetworkServiceManager;

    @Override
    public void start() {
        /**
         * Init the plugin manager
         */
        chatManager = new ChatSupAppModuleManager(chatMiddlewareManager, chatIdentityManager, pluginFileSystem, chatActorConnectionManager, pluginId, this, chatActorNetworkServiceManager);

        System.out.println("******* Init Chat Sup App Module Chat ******");
    }

    @Override
    public ModuleManager<ChatPreferenceSettings, ActiveActorIdentityInformation> getModuleManager() throws CantGetModuleManagerException {
        if (chatManager == null)
            chatManager = new ChatSupAppModuleManager(chatMiddlewareManager, chatIdentityManager, pluginFileSystem, chatActorConnectionManager, pluginId, this, chatActorNetworkServiceManager);
        return chatManager;
    }
}
