package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.FlagNotification;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_module.notification.NotificationType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.P2pEventType;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationEvent;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.notifications.FermatNotificationListener;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers.ClientConnectionCloseNotificationHandler;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers.ClientConnectionLooseNotificationHandler;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers.CloudClientNotificationHandler;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.exceptions.CantCreateNotification;
import com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import java.util.Queue;

//import com.bitdubai.fermat_ccp_api.all_definition.util.WalletUtils;
//import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
//import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
//import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;
//import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
//import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraUserException;
//import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.IntraUserNotFoundException;
//import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;

/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 * <p/>
 * Created by Matias Furszyfer
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class NotificationSubAppModulePluginRoot extends AbstractPlugin implements NotificationManagerMiddleware {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

//    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.ACTOR           , plugin = Plugins.INTRA_WALLET_USER)
//    private IntraWalletUserActorManager intraWalletUserActorManager;
//
//    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.ACTOR           , plugin = Plugins.EXTRA_WALLET_USER)
//    private ExtraUserManager extraUserManager;

    // TODO MAKE USE OF THE ERROR MANAGER


    public NotificationSubAppModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    final String INCOMING_EXTRA_USER_EVENT_STRING = "Incoming Money";

    /**
     * PlatformService Interface member variables.
     */
    List<FermatEventListener> listenersAdded = new ArrayList<>();

    /**
     * Events pool
     */
    Queue<NotificationEvent> poolNotification;


    /**
     * Intra User
     */


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

    private void setUpEventListeners() {

        FermatEventListener fermatEventListenerCloudClientConnectedNotification = eventManager.getNewListener(P2pEventType.COMPLETE_CLIENT_COMPONENT_REGISTRATION_NOTIFICATION);
        FermatEventHandler CloudClietNotificationHandler = new CloudClientNotificationHandler(this);
        fermatEventListenerCloudClientConnectedNotification.setEventHandler(CloudClietNotificationHandler);
        eventManager.addListener(fermatEventListenerCloudClientConnectedNotification);
        listenersAdded.add(fermatEventListenerCloudClientConnectedNotification);

      /*  FermatEventListener fermatEventListenerNewNotification = eventManager.getNewListener(EventType.INCOMING_MONEY_NOTIFICATION);
        FermatEventHandler fermatEventHandlerNewNotification = new IncomingMoneyNotificationHandler(this);
        fermatEventListenerNewNotification.setEventHandler(fermatEventHandlerNewNotification);
        eventManager.addListener(fermatEventListenerNewNotification);
        listenersAdded.add(fermatEventListenerNewNotification);


        FermatEventListener fermatEventListenerIncomingRequestConnectionNotification = eventManager.getNewListener(EventType.INCOMING_INTRA_ACTOR_REQUUEST_CONNECTION_NOTIFICATION);
        FermatEventHandler incomingRequestConnectionNotificationHandler = new com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers.IncomingRequestConnectionNotificationHandler(this);
        fermatEventListenerIncomingRequestConnectionNotification.setEventHandler(incomingRequestConnectionNotificationHandler);
        eventManager.addListener(fermatEventListenerIncomingRequestConnectionNotification);
        listenersAdded.add(fermatEventListenerIncomingRequestConnectionNotification);

        FermatEventListener outgoingIntraUserRollbackTransactionNotificationEventListener = eventManager.getNewListener(EventType.OUTGOING_ROLLBACK_NOTIFICATION);
        FermatEventHandler outgoingRollbackNotificationHandler = new com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers.OutgoingIntraRollbackNotificationHandler(this);
        outgoingIntraUserRollbackTransactionNotificationEventListener.setEventHandler(outgoingRollbackNotificationHandler);
        eventManager.addListener(outgoingIntraUserRollbackTransactionNotificationEventListener);
        listenersAdded.add(outgoingIntraUserRollbackTransactionNotificationEventListener);

        //receive new payment request
        FermatEventListener receivePyamentRequestNotificationEventListener = eventManager.getNewListener(EventType.RECEIVE_PAYMENT_REQUEST_NOTIFICATION);
        FermatEventHandler receivePyamentRequestNotificationHandler = new com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers.ReceivePaymentRequestNotificationHandler(this);
        receivePyamentRequestNotificationEventListener.setEventHandler(receivePyamentRequestNotificationHandler);
        eventManager.addListener(receivePyamentRequestNotificationEventListener);
        listenersAdded.add(receivePyamentRequestNotificationEventListener);

        //denied payment request
        FermatEventListener deniedPaymentRequestNotificationEventListener = eventManager.getNewListener(EventType.DENIED_PAYMENT_REQUEST_NOTIFICATION);
        FermatEventHandler deniedPaymentRequestNotificationHandler = new com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers.DeniedPaymentRequestNotificationHandler(this);
        deniedPaymentRequestNotificationEventListener.setEventHandler(deniedPaymentRequestNotificationHandler);
        eventManager.addListener(deniedPaymentRequestNotificationEventListener);
        listenersAdded.add(deniedPaymentRequestNotificationEventListener);*/

        //close connection server
        FermatEventListener clientConnectionCloseNotificationEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
        FermatEventHandler clientConnectionCloseNotificationHandler = new ClientConnectionCloseNotificationHandler(this);
        clientConnectionCloseNotificationEventListener.setEventHandler(clientConnectionCloseNotificationHandler);
        eventManager.addListener(clientConnectionCloseNotificationEventListener);
        listenersAdded.add(clientConnectionCloseNotificationEventListener);

        //loose connection server
        FermatEventListener clientConnectionLooseNotificationEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
        FermatEventHandler clientConnectionLooseNotificationHandler = new ClientConnectionLooseNotificationHandler(this);
        clientConnectionLooseNotificationEventListener.setEventHandler(clientConnectionLooseNotificationHandler);
        eventManager.addListener(clientConnectionLooseNotificationEventListener);
        listenersAdded.add(clientConnectionLooseNotificationEventListener);

        //connect connection server
        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.COMPLETE_COMPONENT_REGISTRATION_NOTIFICATION);
        fermatEventListener.setEventHandler(new CloudClientNotificationHandler(this));
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);


    }

    @Override
    public void addIncomingExtraUserNotification(EventSource eventSource, String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType) {
        // try {

        //poolNotification = new LinkedList();

        try {
            com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification notification = createNotification(eventSource, "", walletPublicKey, amount, cryptoCurrency, actorId, actorType);
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

    @Override
    public void addIncomingIntraUserNotification(EventSource eventSource, String intraUserIdentityPublicKey, String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType) {
        // try {

        //poolNotification = new LinkedList();

        try {
            com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification notification = createNotification(eventSource, intraUserIdentityPublicKey, walletPublicKey, amount, cryptoCurrency, actorId, actorType);
            notification.setNotificationType(NotificationType.INCOMING_MONEY.getCode());
            poolNotification.add(notification);

            notificationListener.notificate(notification);
        } catch (CantCreateNotification cantCreateNotification) {
            cantCreateNotification.printStackTrace();
        }

        // notify observers
        notifyNotificationArrived();


    }

    private com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification createNotification(EventSource eventSource, String intraUserIdentityPublicKey, String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType) throws CantCreateNotification {
//        try {

//            Actor actor = null;
//            try{
//                actor = getActor(intraUserIdentityPublicKey,actorId,actorType);
//            } catch (IntraUserNotFoundException e) {
//                e.printStackTrace();
//            }
//
//
//            com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification notification = new com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification();
//            notification.setAlertTitle(getSourceString(eventSource) + " " + WalletUtils.formatBalanceString(amount));
//
//            if(actor != null)
//            {
//                notification.setImage(actor.getPhoto());
//                if(cryptoCurrency!=null)
//                notification.setTextBody(actor.getName() + makeString(eventSource) + WalletUtils.formatBalanceString(amount) + " in " + cryptoCurrency.getCode());
//                else{
//                    notification.setTextBody(actor.getName() + makeString(eventSource) + WalletUtils.formatBalanceString(amount) + " in BTC");
//                }
//
//            }
//            else
//            {
//                if(cryptoCurrency!=null)
//                notification.setTextBody( makeString(eventSource) + WalletUtils.formatBalanceString(amount) + " in " + cryptoCurrency.getCode());
//                else notification.setTextBody( makeString(eventSource) + WalletUtils.formatBalanceString(amount) + " in BTC");
//
//            }

//
//            notification.setTextTitle(getTextTitleBySource(eventSource));
//
//            notification.setWalletPublicKey(walletPublicKey);
//
//
//            return notification;
//
//        } catch (CantGetExtraUserException e) {
//            e.printStackTrace();
//            throw new CantCreateNotification();
//
//        } catch (CantGetIntraUserException e) {
//            e.printStackTrace();
//            throw new CantCreateNotification();
//        } catch (ExtraUserNotFoundException e) {
//            e.printStackTrace();
//            throw new CantCreateNotification();
//        }
        return null;
    }

    private com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification createNotification(EventSource eventSource, String actorId, String actorName, Actors actorType, byte[] profileImage) throws CantCreateNotification {
        try {

            com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification notification = new com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification();
            notification.setAlertTitle("Nuevo pedido de conexión!!!");

            notification.setImage(profileImage);

            //  notification.setTextTitle("Soy una bestia");

            notification.setTextBody("Se recibió un pedido de conexion de " + actorName);

            return notification;

        } catch (Exception e) {
            throw new CantCreateNotification();
        }

    }

    private com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification createNotification(EventSource eventSource, String text) throws CantCreateNotification {

        com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification notification = new com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification();
        notification.setAlertTitle(getSourceString(eventSource));

        notification.setTextTitle(getTextTitleBySource(eventSource));

        notification.setTextBody(text);

        return notification;

    }


    public void deleteObserver(Observer observer) {
        this.flagNotification.deleteObservers();
    }

    @Override
    public void addPopUpNotification(EventSource source, String text) {
        try {
            com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification notification = createNotification(source, text);
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
            com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification notification = createNotification(source, actorId, actorName, actorType, profileImage);
            notification.setNotificationType(NotificationType.INCOMING_INTRA_ACTOR_REQUEST_CONNECTION_NOTIFICATION.getCode());
            poolNotification.add(notification);

            //notificationListener.notificate(notification);
        } catch (CantCreateNotification cantCreateNotification) {
            cantCreateNotification.printStackTrace();
        }

        // notify observers
        notifyNotificationArrived();
    }

    @Override
    public void addOutgoingRollbackNotification(EventSource source, String actorId, long amount) {
        try {
            com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification notification = new com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification();

            notification.setAlertTitle(getSourceString(source));
            notification.setTextTitle("Sent Transaction reversed");
//            notification.setTextBody("Sending " + WalletUtils.formatBalanceString(amount)  + " BTC could not be completed.");
            notification.setNotificationType(NotificationType.OUTGOING_INTRA_ACTOR_ROLLBACK_TRANSACTION_NOTIFICATION.getCode());

            poolNotification.add(notification);

            notificationListener.notificate(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // notify observers
        notifyNotificationArrived();
    }

    @Override
    public void addReceiveRequestPaymentNotification(EventSource source, CryptoCurrency cryptoCurrency, long amount)

    {
        try {
            com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification notification = new com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification();

            notification.setAlertTitle(getSourceString(source));
            notification.setTextTitle("");
//            notification.setTextBody("You have received a Payment Request, for " + WalletUtils.formatBalanceString(amount)  + " " + cryptoCurrency.getCode());
            notification.setNotificationType(NotificationType.RECEIVE_REQUEST_PAYMENT_NOTIFICATION.getCode());

            poolNotification.add(notification);

            notificationListener.notificate(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // notify observers
        notifyNotificationArrived();
    }

    @Override
    public void addDeniedRequestPaymentNotification(EventSource source, CryptoCurrency cryptoCurrency, long amount)

    {
        try {
            Notification notification = new com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.structure.Notification();

            notification.setAlertTitle(getSourceString(source));
            notification.setTextTitle("");
//            notification.setTextBody("Your Payment Request, for " + WalletUtils.formatBalanceString(amount)  + " " + cryptoCurrency.getCode() + ", was deny.");
            notification.setNotificationType(NotificationType.DENIED_REQUEST_PAYMENT_NOTIFICATION.getCode());

            poolNotification.add(notification);

            notificationListener.notificate(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // notify observers
        notifyNotificationArrived();
    }

    @Override
    public void addNotificacion(NotificationType notificationType) {
        Notification notification = new Notification();
        switch (notificationType) {
            case CLOUD_CLIENT_CONNECTED:
                notification.setAlertTitle("running");
                notification.setTextTitle("");
                notification.setTextBody("running");
                notification.setNotificationType(NotificationType.CLOUD_CONNECTED_NOTIFICATION.getCode());
                break;
            case CLOUD_CLIENT_CLOSED:
                notification.setAlertTitle("closed");
                notification.setTextTitle("");
                notification.setTextBody("closed");
                notification.setNotificationType(NotificationType.CLOUD_CLIENT_CLOSED.getCode());
                break;
            case CLOUD_CLIENT_CONNECTION_LOOSE:
                notification.setAlertTitle("stopped");
                notification.setTextTitle("");
                notification.setTextBody("stopped");
                notification.setNotificationType(NotificationType.CLOUD_CLIENT_CONNECTION_LOOSE.getCode());
                break;
        }


        poolNotification.add(notification);
        // notificationListener.notificate(notification);
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


    private void notifyNotificationArrived() {

        this.flagNotification.setActive(true);
    }


    private String getTextTitleBySource(EventSource eventSource) {
        switch (eventSource) {
            case INCOMING_EXTRA_USER:
                return "Received money";
            case INCOMING_INTRA_USER:
                return "Received money";
            case OUTGOING_INTRA_USER:
                return "Transaction canceled";
            default:
                return "Method: getTextTitleBySource - NO TIENE valor ASIGNADO para RETURN";
        }
    }

    private String makeString(EventSource eventSource) {
        switch (eventSource) {
            case INCOMING_INTRA_USER:
                return " send ";
            case INCOMING_EXTRA_USER:
                return " send ";
            default:
                return "Method: makeString - NO TIENE valor ASIGNADO para RETURN";

        }

    }

    private String getSourceString(EventSource eventSource) {
        switch (eventSource) {

            case INCOMING_EXTRA_USER:
                return INCOMING_EXTRA_USER_EVENT_STRING;
            default:
                return "Method: getSourceString - NO TIENE valor ASIGNADO para RETURN";

        }

    }
//    private Actor getActor(String intraUserLoggedInPublicKey,String actorId, Actors actorType) throws CantGetExtraUserException, ExtraUserNotFoundException , CantGetIntraUserException, IntraUserNotFoundException{
//        switch (actorType) {
//            case EXTRA_USER:
//
//                    return extraUserManager.getActorByPublicKey(actorId);
//
//            case INTRA_USER:
//
//                    //find actor connected with logget identity
//                try {
//                    return intraWalletUserActorManager.getActorByPublicKey(intraUserLoggedInPublicKey,actorId);
//                }catch (Exception e){
//                    return null;
//                }
//
//
//            default:
//                return null;
//        }
//
//    }


    @Override
    public synchronized Queue<NotificationEvent> getPoolNotification() {
        return poolNotification;
    }

    @Override
    public void addObserver(Observer observer) {
        flagNotification.addObserver(observer);
    }
}
