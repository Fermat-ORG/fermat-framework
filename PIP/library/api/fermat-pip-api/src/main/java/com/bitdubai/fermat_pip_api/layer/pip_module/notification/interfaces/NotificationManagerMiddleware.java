package com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_pip_api.layer.notifications.FermatNotificationListener;

import java.util.Observer;
import java.util.Queue;

import javax.management.NotificationListener;


/**
 * Created by Matias Furszyfer on 2015.08.18..
 */

public interface NotificationManagerMiddleware extends FermatManager {

    void addIncomingExtraUserNotification(EventSource eventSource,String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType);

    void addIncomingIntraUserNotification(EventSource eventSource,String intraUserIdentityPublicKey, String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType);

        Queue<NotificationEvent> getPoolNotification();

    void addObserver(Observer observer);

    void deleteObserver(Observer observer);

    void addPopUpNotification(EventSource source, String s);

    void addIncomingRequestConnectionNotification(EventSource source, String actorId, String actorName, Actors actorType, byte[] profileImage);

    void addCallback(FermatNotificationListener notificationListener);

    void deleteCallback(FermatNotificationListener fermatNotificationListener);
}
