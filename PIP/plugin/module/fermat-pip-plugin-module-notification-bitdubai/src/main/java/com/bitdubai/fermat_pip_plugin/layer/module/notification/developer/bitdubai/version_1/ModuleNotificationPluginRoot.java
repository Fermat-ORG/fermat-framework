package com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.event_handlers.NewNotificationHandler;
import com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.structure.NotificationEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Matias Furszyfer
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class ModuleNotificationPluginRoot implements  DealsWithErrors,DealsWithEvents, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem,NotificationManager, Service, Plugin {


    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPlatformFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;
    /**
     * PlatformService Interface member variables.
     */
    List<EventListener> listenersAdded = new ArrayList<>();

    /**
     * Deals with event manager
     */
    private EventManager eventManager;


    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    /**
     * DeviceUser Interface member variables
     */
    UUID pluginId;

    /**
     * ServiceStatus Interface member variables
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     *  Events pool
     */
    List<NotificationEvent> poolNotification;


    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {
        try {
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (Exception exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
       // if (errorManager == null)
       //     throw new IllegalArgumentException();
        this.errorManager = errorManager;
    }

    public static LogLevel getLogLevelByClass(String className) {
        try {
            String[] correctedClass = className.split((Pattern.quote("$")));
            return ModuleNotificationPluginRoot.newLoggingLevel.get(correctedClass[0]);
        } catch (Exception exception) {
            System.err.println("CantGetLogLevelByClass: " + exception.getMessage());
            return LogLevel.MODERATE_LOGGING;
        }
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem=pluginFileSystem;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    private void setUpEventListeners(){

        EventListener eventListenerNewNotification = eventManager.getNewListener(EventType.NEW_NOTIFICATION);
        EventHandler eventHandlerNewNotification = new NewNotificationHandler(this);
        eventListenerNewNotification.setEventHandler(eventHandlerNewNotification);
        eventManager.addListener(eventListenerNewNotification);
        listenersAdded.add(eventListenerNewNotification);


    }

    @Override
    public void addNotification(String notificationTitle, String textTitle, String textBody) {

    }

    @Override
    public List<com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationEvent> getPoolNotification() {
        return null;
    }
}
