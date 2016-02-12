package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.WsCommunicationsCloudClientManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Temporal class this modifications must be done in abstract network service base.
 */
public abstract class AbstractNetworkServiceBaseTemp extends AbstractNetworkServiceBase implements NetworkService {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    protected ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    protected EventManager eventManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    protected PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    protected Broadcaster broadcaster;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.LOG_MANAGER)
    protected LogManager logManager;

    @NeededPluginReference(platform = Platforms.COMMUNICATION_PLATFORM, layer = Layers.COMMUNICATION, plugin = Plugins.WS_CLOUD_CLIENT)
    protected WsCommunicationsCloudClientManager wsCommunicationsCloudClientManager;

    public AbstractNetworkServiceBaseTemp(
            PluginVersionReference pluginVersionReference,
            EventSource eventSource,
            PlatformComponentType platformComponentType,
            NetworkServiceType networkServiceType,
            String name,
            String extraData) {

        super(
                pluginVersionReference,
                eventSource,
                platformComponentType,
                networkServiceType,
                name,
                extraData
        );
    }

    @Override
    public PlatformComponentProfile getProfileDestinationToRequestConnection(String identityPublicKeyDestination) {
        return null;
    }

    @Override
    public PlatformComponentProfile getProfileSenderToRequestConnection(String identityPublicKeySender) {
        return null;
    }

    @Override
    public void onNewMessagesReceive(FermatMessage newFermatMessageReceive) {

    }

    @Override
    public void onSentMessage(FermatMessage messageSent) {

    }

    @Override
    protected void reprocessMessages() {

    }

    @Override
    protected void reprocessMessages(String identityPublicKey) {

    }

    @Override
    protected void onClientConnectionClose() {

    }

    @Override
    protected void onClientSuccessfulReconnect() {

    }

    @Override
    protected void onClientConnectionLoose() {

    }

    @Override
    protected void onNetworkServiceRegistered() {

    }

    @Override
    protected void onFailureComponentConnectionRequest(PlatformComponentProfile remoteParticipant) {

        checkFailedDeliveryTime(remoteParticipant.getIdentityPublicKey());
    }

    @Override
    protected void onReceivePlatformComponentProfileRegisteredList(CopyOnWriteArrayList<PlatformComponentProfile> remotePlatformComponentProfileRegisteredList) {

    }

    @Override
    protected void onCompleteActorProfileUpdate(PlatformComponentProfile platformComponentProfileUpdate) {

    }

    @Override
    protected void onFailureComponentRegistration(PlatformComponentProfile remoteParticipant) {

    }

    protected void checkFailedDeliveryTime(String publicKey) {

    }

    @Override
    protected CommunicationsClientConnection getCommunicationsClientConnection() {

        return wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection();
    }

    @Override
    public ErrorManager getErrorManager() {

        return errorManager;
    }

    @Override
    public EventManager getEventManager() {

        return eventManager;
    }

    @Override
    public WsCommunicationsCloudClientManager getWsCommunicationsCloudClientManager() {

        return wsCommunicationsCloudClientManager;
    }

    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {

        return pluginDatabaseSystem;
    }

    @Override
    public PluginFileSystem getPluginFileSystem() {

        return pluginFileSystem;
    }

    @Override
    public Broadcaster getBroadcaster() {

        return broadcaster;
    }

    @Override
    public LogManager getLogManager() {

        return logManager;
    }

}
