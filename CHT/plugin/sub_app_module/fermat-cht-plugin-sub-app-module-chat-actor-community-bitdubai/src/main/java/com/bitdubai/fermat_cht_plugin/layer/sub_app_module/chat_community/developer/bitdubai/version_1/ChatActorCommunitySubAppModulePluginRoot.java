package com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_cht_api.layer.actor_connection.interfaces.ChatActorConnectionManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentityManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.settings.ChatActorCommunitySettings;
import com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure.ChatActorCommunityManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;


/**
 * Created by Eleazar (eorono@protonmail.com) on 2/4/2016.
 * Edited by Miguel Rincon 18/04/2016
 */
@PluginInfo(createdBy = "Eleazar Orono", maintainerMail = "franklinmarcano1970@gmail.com", platform = Platforms.CHAT_PLATFORM, layer = Layers.SUB_APP_MODULE, plugin = Plugins.CHAT_COMMUNITY_SUP_APP_MODULE)
public class ChatActorCommunitySubAppModulePluginRoot extends AbstractModule<ChatActorCommunitySettings, ChatActorCommunitySelectableIdentity> {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    private LocationManager locationManager;

    @NeededPluginReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.EXTERNAL_API, plugin = Plugins.GEOLOCATION)
    private GeolocationManager geolocationManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.ACTOR_CONNECTION, plugin = Plugins.CHAT_ACTOR_CONNECTION)
    private ChatActorConnectionManager chatActorConnectionManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CHAT_ACTOR_NETWORK_SERVICE)
    private ChatManager chatActorNetworkServiceManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CHAT_IDENTITY)
    private ChatIdentityManager chatIdentityManager;

    private ChatActorCommunitySettings chatActorCommunitySettings = new ChatActorCommunitySettings();
    ChatActorCommunityManager fermatManager;

    public ChatActorCommunitySubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));

    }

    @Override
    public void start() throws CantStartPluginException {

        try {

            System.out.println("******* Init Chat Sup App Module Actor Connection ******");

            fermatManager = new ChatActorCommunityManager(
                    chatIdentityManager,
                    chatActorConnectionManager,
                    chatActorNetworkServiceManager,
                    this,
                    pluginFileSystem,
                    pluginId,
                    getPluginVersionReference(),
                    geolocationManager,
                    locationManager
            );

            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.CHAT_COMMUNITY_SUP_APP_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception),
                    null,
                    null);
        }
    }


    @Override
    public ModuleManager<ChatActorCommunitySettings, ChatActorCommunitySelectableIdentity> getModuleManager() throws CantGetModuleManagerException {
        if (fermatManager == null) {
            fermatManager = new ChatActorCommunityManager(
                    chatIdentityManager,
                    chatActorConnectionManager,
                    chatActorNetworkServiceManager,
                    this,
                    pluginFileSystem,
                    pluginId,
                    getPluginVersionReference(),
                    geolocationManager,
                    locationManager
            );
        }
        return fermatManager;
    }

    public ChatActorCommunityManager getChatActorCommunityManager() {
        return fermatManager;
    }

    public ChatActorCommunitySettings getChatActorCommunitySettings() {
        return chatActorCommunitySettings;
    }

    public void setChatActorCommunitySettings(ChatActorCommunitySettings chatActorCommunitySettings) {
        this.chatActorCommunitySettings = chatActorCommunitySettings;
    }
}
