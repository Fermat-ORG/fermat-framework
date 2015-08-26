package com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.NewNotificationEvent;
import com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.structure.NotificationEvent;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */
public class NewNotificationHandler implements EventHandler {


    NotificationManager notificationManager;

    public NewNotificationHandler(final NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {

        NewNotificationEvent newNotificationEvent =(NewNotificationEvent)platformEvent;

        NotificationEvent notificationEvent = new NotificationEvent();
        notificationEvent.setAlertTitle(newNotificationEvent.getNotificationTitle());
        notificationEvent.setTextTitle(newNotificationEvent.getNotificationTextTitle());
        notificationEvent.setTextBody(newNotificationEvent.getNotificationTextBody());

        //TODO: falta ver si le seteo la activity (enum de las activities) o/y si le setio la public Key de la wallet


        System.out.println("PROBANDO EVENTO MATI, PARA NOTIFICACIONES");

        if (((Service) this.notificationManager).getStatus() == ServiceStatus.STARTED) {

            System.out.println("PROBANDO EVENTO MATI, PARA NOTIFICACIONES 2");
            //TODO: acá hay que implementar el add al pool de notificaciones
            //this.notificationManager.recordNavigationStructure(xmlText,link,filename,skinId);


        }

    }
}
