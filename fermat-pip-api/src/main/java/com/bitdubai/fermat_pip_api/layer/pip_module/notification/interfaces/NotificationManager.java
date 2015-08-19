package com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */

public interface NotificationManager {

    public void addNotification(String notificationTitle,String textTitle,String textBody);

    public List<NotificationEvent> getPoolNotification();

}
