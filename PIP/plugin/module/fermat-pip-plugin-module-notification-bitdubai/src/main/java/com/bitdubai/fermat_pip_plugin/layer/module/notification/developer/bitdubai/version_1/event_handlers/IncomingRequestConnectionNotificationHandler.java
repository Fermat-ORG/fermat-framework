package com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingActorRequestConnectionNotificationEvent;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */
public class IncomingRequestConnectionNotificationHandler implements FermatEventHandler {


    NotificationManagerMiddleware notificationManager;

    public IncomingRequestConnectionNotificationHandler(final NotificationManagerMiddleware notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        IncomingActorRequestConnectionNotificationEvent incomingActorRequestConnectionNotificationEvent =(IncomingActorRequestConnectionNotificationEvent) fermatEvent;

        //NotificationEvent notificationEvent = new NotificationEvent();
        //notificationEvent.setAlertTitle(newNotificationEvent.getNotificationTitle());
        //notificationEvent.setTextTitle(newNotificationEvent.getNotificationTextTitle());
        //notificationEvent.setTextBody(newNotificationEvent.getNotificationTextBody());


        //TODO: falta ver si le seteo la activity (enum de las activities) o/y si le setio la public Key de la wallet


        System.out.println("PROBANDO EVENTO MATI, PARA NOTIFICACIONES");

        if (((Service) this.notificationManager).getStatus() == ServiceStatus.STARTED) {

            System.out.println("PROBANDO EVENTO MATI, PARA NOTIFICACIONES 2");
            //TODO: acá hay que implementar el add al pool de notificaciones
            notificationManager.addIncomingRequestConnectionNotification(incomingActorRequestConnectionNotificationEvent.getSource(), incomingActorRequestConnectionNotificationEvent.getActorId(), incomingActorRequestConnectionNotificationEvent.getActorName(), incomingActorRequestConnectionNotificationEvent.getActorType(), incomingActorRequestConnectionNotificationEvent.getProfileImage());
            //this.notificationManager.recordNavigationStructure(xmlText,link,filename,skinId);


        }

    }
}
