package com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.event.FlagNotification;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.interfaces.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationEvent;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IncomingMoneyNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.WalletUninstalledEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.event_handlers.IncomingMoneyNotificationHandler;
import com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.exceptions.CantCreateNotification;
import com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.structure.Notification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Queue;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Matias Furszyfer
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class ModuleNotificationPluginRoot implements DealsWithExtraUsers,DealsWithErrors,DealsWithEvents, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem,NotificationManagerMiddleware, Service, Plugin {

    final String INCOMING_EXTRA_USER_EVENT_STRING = "Incoming Money";

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
    Queue<NotificationEvent> poolNotification;

    /**
     * Extra users
     */
    private ExtraUserManager extraUserManager;

    /**
     *  Notification flag
     *  wil be true when queue of notification is not clean
     */
    private boolean notificationFlag;

    FlagNotification flagNotification;

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {

        setUpEventListeners();


        try {
            flagNotification = new FlagNotification();
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

        EventListener eventListenerNewNotification = eventManager.getNewListener(EventType.INCOMING_MONEY_NOTIFICATION);
        EventHandler eventHandlerNewNotification = new IncomingMoneyNotificationHandler(this);
        eventListenerNewNotification.setEventHandler(eventHandlerNewNotification);
        eventManager.addListener(eventListenerNewNotification);
        listenersAdded.add(eventListenerNewNotification);


    }

    @Override
    public void addIncomingExtraUserNotification(EventSource eventSource,String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType) {
       // try {

        poolNotification = new LinkedList();

            //poolNotification.add(createNotification(eventSource, walletPublicKey, amount, cryptoCurrency, actorId, actorType));
            Notification notification = new Notification();
            notification.setAlertTitle("Sos capo pibe");
            notification.setTextTitle("Ganaste un premio");
            notification.setTextBody("5000 btc");
            poolNotification.add(notification);
            // notify observers
            notifyNotificationArrived();

//        } catch (CantCreateNotification cantCreateNotification) {
//            cantCreateNotification.printStackTrace();
//        }
    }

    private Notification createNotification(EventSource eventSource,String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType) throws CantCreateNotification {
        try {
            Actor actor = getActor(actorId);

            Notification notification = new Notification();
            notification.setAlertTitle(getSourceString(eventSource) + " " + amount);

            notification.setImage(actor.getPhoto());

            notification.setTextTitle(getTextTitleBySource(eventSource));

            notification.setWalletPublicKey(walletPublicKey);

            notification.setTextBody(actor.getName() + makeString(eventSource) + amount + " in " + cryptoCurrency.getCode());

            return notification;

        } catch (CantGetExtraUserException e) {
            e.printStackTrace();
        } catch (ExtraUserNotFoundException e) {
            e.printStackTrace();
        }
        throw new CantCreateNotification();
    }

    private void notifyNotificationArrived(){
        this.flagNotification.setActive(true);
    }



    private String getTextTitleBySource(EventSource eventSource){
        switch (eventSource) {
            case INCOMING_EXTRA_USER:
                return "Received money";
            default:
                return null;
        }
    }

    private String makeString(EventSource eventSource){
        switch (eventSource){

            case INCOMING_EXTRA_USER:
                return " send ";
            default:
                return null;

        }

    }

    private String getSourceString(EventSource eventSource){
        switch (eventSource){

            case INCOMING_EXTRA_USER:
                return INCOMING_EXTRA_USER_EVENT_STRING;
            default:
                return null;

        }

    }
    private Actor getActor(String actorId) throws CantGetExtraUserException, ExtraUserNotFoundException {
        return extraUserManager.getActorByPublicKey(actorId);
    }


    @Override
    public Queue<NotificationEvent> getPoolNotification() {
        return poolNotification;
    }

    @Override
    public void addObserver(Observer observer) {
        flagNotification.addObserver(observer);
    }


    public void raiseEvent() {
        /**
         *  Fire event notification
         */

        PlatformEvent platformEvent = eventManager.getNewEvent(EventType.INCOMING_MONEY_NOTIFICATION);
        IncomingMoneyNotificationEvent incomingMoneyNotificationEvent=  (IncomingMoneyNotificationEvent) platformEvent;
        incomingMoneyNotificationEvent.setSource(EventSource.INCOMING_EXTRA_USER);
        eventManager.raiseEvent(platformEvent);
    }


    @Override
    public void setExtraUserManager(ExtraUserManager extraUserManager) {
        this.extraUserManager = extraUserManager;
    }
}
