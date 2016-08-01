package com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantExposeActorIdentitiesException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.database.ChatIdentityDatabaseConstants;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.database.ChatIdentityDatabaseFactory;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.database.ChatIdentityDeveloperFactory;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.exceptions.CantInitializeChatIdentityDatabaseException;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure.ChatIdentityManagerImpl;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franklin on 30/03/16.
 */
@PluginInfo(createdBy = "Franklin Marcano", maintainerMail = "franklinmarcano1970@gmail.com", platform = Platforms.CHAT_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.CHAT_IDENTITY)
public class ChatIdentityPluginRoot extends AbstractPlugin implements
        DatabaseManagerForDevelopers {
    public ChatIdentityPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private ChatIdentityManagerImpl chatIdentityManager;
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.DEVICE_LOCATION)
    private LocationManager locationManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.USER, addon = Addons.DEVICE_USER)
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CHAT_ACTOR_NETWORK_SERVICE)
    private ChatManager chatManager;

    @Override
    public FermatManager getManager() {
        return chatIdentityManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        chatIdentityManager = new ChatIdentityManagerImpl(pluginDatabaseSystem, pluginId, this, deviceUserManager, pluginFileSystem, chatManager, locationManager);
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, ChatIdentityDatabaseConstants.CHAT_DATABASE_NAME);
            System.out.println("******* Init Chat Identity ******");
            //super.start();
            this.serviceStatus = ServiceStatus.STARTED;
            //Expose all identities the device
            exposeIdentities();
        } catch (CantOpenDatabaseException | DatabaseNotFoundException e) {
            try {
                System.out.println("******* Init Chat Identity ******");
                ChatIdentityDatabaseFactory databaseFactory = new ChatIdentityDatabaseFactory(pluginDatabaseSystem);
                databaseFactory.createDatabase(this.pluginId, ChatIdentityDatabaseConstants.CHAT_DATABASE_NAME);
//                chatIdentityManager.registerIdentitiesANSTest();
            } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            } catch (Exception exception) {
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantStartPluginException("Unhandled Exception.", FermatException.wrapException(exception), null, null);
            }
        } catch (CantExposeActorIdentitiesException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Cant expose Actor Identities.", FermatException.wrapException(e), null, null);
        } catch (CantListChatIdentityException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Cant List Chat Identity.", FermatException.wrapException(e), null, null);
        } catch (CantExposeIdentitiesException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Cant Expose Identity.", FermatException.wrapException(e), null, null);
        } catch (CantExposeIdentityException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Cant Update Expose Identity.", FermatException.wrapException(e), null, null);
        }

    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
        //super.stop();
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        ChatIdentityDeveloperFactory developerFactory = new ChatIdentityDeveloperFactory(pluginDatabaseSystem, pluginId);
        return developerFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        ChatIdentityDeveloperFactory developerFactory = new ChatIdentityDeveloperFactory(pluginDatabaseSystem, pluginId);
        return developerFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        ChatIdentityDeveloperFactory developerFactory = new ChatIdentityDeveloperFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            developerFactory.initializeDatabase();
            developerDatabaseTableRecordList = developerFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeChatIdentityDatabaseException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
        return developerDatabaseTableRecordList;
    }

    private void exposeIdentities() throws CantExposeActorIdentitiesException, CantListChatIdentityException, CantExposeIdentitiesException, CantExposeIdentityException {
        List<ChatExposingData> chatExposingDataList = new ArrayList<>();
        Location location = null;
        try {
            location = locationManager.getLocation();
        } catch (CantGetDeviceLocationException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantExposeIdentitiesException("Cant Expose Chat Identity, in Get Location.", FermatException.wrapException(e), null, null);
        }
        long refreshInterval = 0;
        for (final ChatIdentity chatIdentity : chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser()) {
            refreshInterval = chatIdentity.getFrecuency().getRefreshInterval();
            chatExposingDataList.add(
                    new ChatExposingData(
                            chatIdentity.getPublicKey(),
                            chatIdentity.getAlias(),
                            chatIdentity.getImage(),
                            chatIdentity.getCountry(),
                            chatIdentity.getState(),
                            chatIdentity.getCity(),
                            chatIdentity.getConnectionState(),
                            location,
                            refreshInterval,
                            chatIdentity.getAccuracy(),
                            ProfileStatus.UNKNOWN
                    )
            );

        }

        chatManager.exposeIdentities(chatExposingDataList);
    }
}
