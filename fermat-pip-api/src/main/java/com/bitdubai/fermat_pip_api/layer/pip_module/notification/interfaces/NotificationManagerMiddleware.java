package com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;

import java.util.Observer;
import java.util.Queue;


/**
 * Created by Matias Furszyfer on 2015.08.18..
 */

public interface NotificationManagerMiddleware {

    public void addIncomingExtraUserNotification(EventSource eventSource,String walletPublicKey, long amount, CryptoCurrency cryptoCurrency, String actorId, Actors actorType);

    public Queue<NotificationEvent> getPoolNotification();

    public void addObserver(Observer observer);

    public void deleteObserver(Observer observer);

    public void addPopUpNotification(EventSource source, String s);


}
