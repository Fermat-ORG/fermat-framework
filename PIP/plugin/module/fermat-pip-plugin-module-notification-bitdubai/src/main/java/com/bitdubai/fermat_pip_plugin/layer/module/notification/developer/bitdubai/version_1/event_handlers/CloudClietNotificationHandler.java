package com.bitdubai.fermat_pip_plugin.layer.module.notification.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.CompleteComponentRegistrationNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces.NotificationManagerMiddleware;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */
public class CloudClietNotificationHandler implements FermatEventHandler {


    NotificationManagerMiddleware notificationManager;

    public CloudClietNotificationHandler(final NotificationManagerMiddleware notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        CompleteComponentRegistrationNotificationEvent completeComponentRegistrationNotificationEvent =(CompleteComponentRegistrationNotificationEvent) fermatEvent;

        //NotificationEvent notificationEvent = new NotificationEvent();
        //notificationEvent.setAlertTitle(newNotificationEvent.getNotificationTitle());
        //notificationEvent.setTextTitle(newNotificationEvent.getNotificationTextTitle());
        //notificationEvent.setTextBody(newNotificationEvent.getNotificationTextBody());



        System.out.println("PROBANDO EVENTO MATI, PARA NOTIFICACIONES DE ROBERT");

        if (((Service) this.notificationManager).getStatus() == ServiceStatus.STARTED) {

            //TODO: acá hay que implementar el add al pool de notificaciones

            notificationManager.addPopUpNotification(completeComponentRegistrationNotificationEvent.getSource(), completeComponentRegistrationNotificationEvent.getPlatformComponentProfileRegistered().toString());
            //this.notificationManager.recordNavigationStructure(xmlText,link,filename,skinId);


        }

    }
}
