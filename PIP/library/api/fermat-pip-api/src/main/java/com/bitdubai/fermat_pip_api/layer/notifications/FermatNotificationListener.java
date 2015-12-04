package com.bitdubai.fermat_pip_api.layer.notifications;

import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationEvent;

/**
 * Created by mati on 2015.10.20..
 */
public interface FermatNotificationListener {

    public void notificate(NotificationEvent notificationEvent);

}
