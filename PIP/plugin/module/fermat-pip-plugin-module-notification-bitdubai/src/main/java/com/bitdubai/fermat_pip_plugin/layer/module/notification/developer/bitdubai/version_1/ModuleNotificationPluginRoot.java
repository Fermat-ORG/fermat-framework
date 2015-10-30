package com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.FlagNotification;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.dmp_module.notification.NotificationType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_api.all_definition.util.WalletUtils;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.DealsWithExtraUsers;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_pip_api.layer.notifications.FermatNotificationListener;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationEvent;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.event_handlers.CloudClietNotificationHandler;
import com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.event_handlers.IncomingMoneyNotificationHandler;
import com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.event_handlers.IncomingRequestConnectionNotificationHandler;
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
    List<FermatEventListener> listenersAdded = new ArrayList<>();

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

    private FermatNotificationListener notificationListener;

    /**
     * Service Interface implementation.
     */

    @Override
    public void start() throws CantStartPluginException {

        setUpEventListeners();


        this.poolNotification = new LinkedList();


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

        FermatEventListener fermatEventListenerNewNotification = eventManager.getNewListener(EventType.INCOMING_MONEY_NOTIFICATION);
        FermatEventHandler fermatEventHandlerNewNotification = new IncomingMoneyNotificationHandler(this);
        fermatEventListenerNewNotification.setEventHandler(fermatEventHandlerNewNotification);
        eventManager.addListener(fermatEventListenerNewNotification);
        listenersAdded.add(fermatEventListenerNewNotification);

        FermatEventListener fermatEventListenerCloudClientConnectedNotification = eventManager.getNewListener(P2pEventType.COMPLETE_CLIENT_COMPONENT_REGISTRATION_NOTIFICATION);
        FermatEventHandler CloudClietNotificationHandler = new CloudClietNotificationHandler(this);
        fermatEventListenerCloudClientConnectedNotification.setEventHandler(CloudClietNotificationHandler);
        eventManager.addListener(fermatEventListenerCloudClientConnectedNotification);
        listenersAdded.add(fermatEventListenerCloudClientConnectedNotification);

        FermatEventListener fermatEventListenerIncomingRequestConnectionNotification = eventManager.getNewListener(EventType.INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION);
        FermatEventHandler incomingRequestConnectionNotificationHandler = new IncomingRequestConnectionNotificationHandler(this);
        fermatEventListenerIncomingRequestConnectionNotification.setEventHandler(incomingRequestConnectionNotificationHandler);
        eventManager.addListener(fermatEventListenerIncomingRequestConnectionNotification);
        listenersAdded.add(fermatEventListenerIncomingRequestConnectionNotification);


    }

    @Override
    public void addIncomingExtraUserNotification(EventSource eventSource,String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType) {
       // try {

        //poolNotification = new LinkedList();

        try {
            Notification notification = createNotification(eventSource, walletPublicKey, amount, cryptoCurrency, actorId, actorType);
            notification.setNotificationType(NotificationType.INCOMING_MONEY.getCode());
            poolNotification.add(notification);
        } catch (CantCreateNotification cantCreateNotification) {
            cantCreateNotification.printStackTrace();
        }
//            Notification notification = new Notification();
//            notification.setAlertTitle("Sos capo pibe");
//            notification.setTextTitle("Ganaste un premio");
//            notification.setTextBody("5000 btc");
//            poolNotification.add(notification);
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
            notification.setAlertTitle(getSourceString(eventSource) + " " + WalletUtils.formatBalanceString(amount));

            notification.setImage(actor.getPhoto());

            notification.setTextTitle(getTextTitleBySource(eventSource));

            notification.setWalletPublicKey(walletPublicKey);

            notification.setTextBody(actor.getName() + makeString(eventSource) + WalletUtils.formatBalanceString(amount) + " in " + cryptoCurrency.getCode());

            return notification;

        } catch (CantGetExtraUserException e) {
            e.printStackTrace();
        } catch (ExtraUserNotFoundException e) {
            e.printStackTrace();
        }
        throw new CantCreateNotification();
    }

    private Notification createNotification(EventSource eventSource, String actorId, String actorName, Actors actorType, byte[] profileImage) throws CantCreateNotification {
        try {

            Notification notification = new Notification();
            notification.setAlertTitle("Nuevo pedido de conexión!!!");

            notification.setImage(profileImage);

            notification.setTextTitle("Soy una bestia");

            notification.setTextBody("Se recibió un pedido de conexion de " + actorName);

            return notification;

        }catch (Exception e) {
            throw new CantCreateNotification();
        }

    }

    private Notification createNotification(EventSource eventSource,String text) throws CantCreateNotification {

            Notification notification = new Notification();
            notification.setAlertTitle(getSourceString(eventSource));

            notification.setTextTitle(getTextTitleBySource(eventSource));

            notification.setTextBody(text);

            return notification;

    }


    public void deleteObserver(Observer observer){
        this.flagNotification.deleteObservers();
    }

    @Override
    public void addPopUpNotification(EventSource source, String text) {
        try {
            Notification notification = createNotification(source,text);
            notification.setNotificationType(NotificationType.CLOUD_CONNECTED_NOTIFICATION.getCode());
            poolNotification.add(notification);
        } catch (CantCreateNotification cantCreateNotification) {
            cantCreateNotification.printStackTrace();
        }

        // notify observers
        notifyNotificationArrived();

    }

    @Override
    public void addIncomingRequestConnectionNotification(EventSource source, String actorId, String actorName, Actors actorType, byte[] profileImage) {

        try {
            Notification notification = createNotification(source, actorId, actorName, actorType, profileImage);
            notification.setNotificationType(NotificationType.INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION.getCode());
            poolNotification.add(notification);

            notificationListener.notificate(notification);
        } catch (CantCreateNotification cantCreateNotification) {
            cantCreateNotification.printStackTrace();
        }

        // notify observers
        notifyNotificationArrived();
    }

    @Override
    public void addCallback(FermatNotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    @Override
    public void deleteCallback(FermatNotificationListener fermatNotificationListener) {
        //esto deberia ser un hashmap
        this.notificationListener = null;
    }



    private void notifyNotificationArrived(){

        this.flagNotification.setActive(true);
    }



    private String getTextTitleBySource(EventSource eventSource){
        switch (eventSource) {
            case INCOMING_EXTRA_USER:
                return "Received money";
            default:
                return "Method: getTextTitleBySource - NO TIENE valor ASIGNADO para RETURN";
        }
    }

    private String makeString(EventSource eventSource){
        switch (eventSource){

            case INCOMING_EXTRA_USER:
                return " send ";
            default:
                return "Method: makeString - NO TIENE valor ASIGNADO para RETURN";

        }

    }

    private String getSourceString(EventSource eventSource){
        switch (eventSource){

            case INCOMING_EXTRA_USER:
                return INCOMING_EXTRA_USER_EVENT_STRING;
            default:
                return "Method: getSourceString - NO TIENE valor ASIGNADO para RETURN";

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

        FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_MONEY_NOTIFICATION);
        IncomingMoneyNotificationEvent incomingMoneyNotificationEvent=  (IncomingMoneyNotificationEvent) fermatEvent;
        incomingMoneyNotificationEvent.setSource(EventSource.INCOMING_EXTRA_USER);
        eventManager.raiseEvent(fermatEvent);
    }


    @Override
    public void setExtraUserManager(ExtraUserManager extraUserManager) {
        this.extraUserManager = extraUserManager;
    }
}
