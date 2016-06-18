package com.bitdubai.fermat_pip_api.layer.module.notification.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.dmp_module.notification.NotificationType;
import com.bitdubai.fermat_pip_api.layer.notifications.FermatNotificationListener;

import java.util.Observer;
import java.util.Queue;


/**
 * Created by Matias Furszyfer on 2015.08.18..
 */

public interface NotificationManagerMiddleware extends FermatManager {

    @Deprecated
    void addIncomingExtraUserNotification(EventSource eventSource, String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType);

    @Deprecated
    void addIncomingIntraUserNotification(EventSource eventSource, String intraUserIdentityPublicKey, String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType);

    @Deprecated
    Queue<NotificationEvent> getPoolNotification();

    @Deprecated
    void addObserver(Observer observer);

    @Deprecated
    void deleteObserver(Observer observer);

    @Deprecated
    void addPopUpNotification(EventSource source, String s);

    @Deprecated
    void addIncomingRequestConnectionNotification(EventSource source, String actorId, String actorName, Actors actorType, byte[] profileImage);

    @Deprecated
    void addOutgoingRollbackNotification(EventSource source, String actorId, long amount);

    @Deprecated
    void addCallback(FermatNotificationListener notificationListener);

    @Deprecated
    void deleteCallback(FermatNotificationListener fermatNotificationListener);

    @Deprecated
    void addReceiveRequestPaymentNotification(EventSource source, CryptoCurrency cryptoCurrency, long amount);

    @Deprecated
    void addDeniedRequestPaymentNotification(EventSource source, CryptoCurrency cryptoCurrency, long amount);

    @Deprecated
    void addNotificacion(NotificationType notificationType);
}
