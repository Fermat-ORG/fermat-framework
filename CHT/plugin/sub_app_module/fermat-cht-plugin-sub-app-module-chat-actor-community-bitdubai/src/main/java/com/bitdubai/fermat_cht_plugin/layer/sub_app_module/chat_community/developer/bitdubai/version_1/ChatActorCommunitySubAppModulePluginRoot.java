package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.ActorConnectionNotFoundException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDisconnectFromActorException;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.UnexpectedConnectionStateException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatConnectionAlreadyRequestesException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorChatTypeNotSupportedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantAcceptChatRequestException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatActorException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantListChatIdentitiesToSelectException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantRequestActorConnectionException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantValidateActorConnectionStateException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorCancellingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorConnectionDenialFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorDisconnectingFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySearch;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure.ChatActorCommunityManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;

import java.util.List;
import java.util.UUID;


/**
 * Created by Eleazar (eorono@protonmail.com) on 2/4/2016.
 * Edited by Miguel Rincon 18/04/2016
 */

public class ChatActorCommunitySubAppModulePluginRoot extends AbstractModule<ChatActorCommunitySettings, ChatActorCommunitySelectableIdentity> {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM     , layer = Layers.PLATFORM_SERVICE     , addon  = Addons.ERROR_MANAGER     )
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API  , layer = Layers.SYSTEM               , addon  = Addons .PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.ACTOR_CONNECTION     , plugin = Plugins.CHAT_ACTOR_CONNECTION)
    private ChatActorConnectionManager chatActorConnectionManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CHAT_ACTOR_NETWORK_SERVICE  )
    private ChatManager chatActorNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.IDENTITY             , plugin = Plugins.CHAT_IDENTITY)
    private ChatIdentityManager chatIdentityManager;

private ChatActorCommunitySettings chatActorCommunitySettings = new ChatActorCommunitySettings();
    ChatActorCommunityManager fermatManager;

    public ChatActorCommunitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));

    }

    @Override
    public void start() throws CantStartPluginException {

        try {
            fermatManager = new ChatActorCommunityManager(
                    chatIdentityManager,
                    chatActorConnectionManager,
                    chatActorNetworkServiceManager,
                    errorManager,
                    pluginFileSystem,
                    pluginId,
                    getPluginVersionReference()
            );

            System.out.println("******* Init Chat Sup App Module Actor Connection ******");

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception),
                    null,
                    null);
        }
    }


    @Override
    public ModuleManager<ChatActorCommunitySettings, ChatActorCommunitySelectableIdentity> getModuleManager() throws CantGetModuleManagerException {
        return fermatManager;
    }

    public ChatActorCommunitySettings getChatActorCommunitySettings() {
        return chatActorCommunitySettings;
    }

    public void setChatActorCommunitySettings(ChatActorCommunitySettings chatActorCommunitySettings) {
        this.chatActorCommunitySettings = chatActorCommunitySettings;
    }
}
