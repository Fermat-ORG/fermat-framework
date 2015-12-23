package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingMoneyNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.OutgoingIntraRollbackNotificationEvent;

/**
 * Created by mati on 2015.12.23..
 */
public class OutgoingIntraRollbackNotificationHandler implements FermatEventHandler {

    NotificationManagerMiddleware notificationManager;

    public OutgoingIntraRollbackNotificationHandler(final NotificationManagerMiddleware notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        OutgoingIntraRollbackNotificationEvent incomingMoneyNotificationEvent =(OutgoingIntraRollbackNotificationEvent) fermatEvent;

        //NotificationEvent notificationEvent = new NotificationEvent();
        //notificationEvent.setAlertTitle(newNotificationEvent.getNotificationTitle());
        //notificationEvent.setTextTitle(newNotificationEvent.getNotificationTextTitle());
        //notificationEvent.setTextBody(newNotificationEvent.getNotificationTextBody());


        //TODO: falta ver si le seteo la activity (enum de las activities) o/y si le setio la public Key de la wallet


        System.out.println("PROBANDO EVENTO Rollback, PARA NOTIFICACIONES");

        if (((Service) this.notificationManager).getStatus() == ServiceStatus.STARTED) {

            System.out.println("PROBANDO EVENTO Rollback, PARA NOTIFICACIONES 2");
            //TODO: acá hay que implementar el add al pool de notificaciones
                    notificationManager.addIncomingIntraUserNotification(incomingMoneyNotificationEvent.getSource(), incomingMoneyNotificationEvent.getIntraUserIdentityPublicKey(),incomingMoneyNotificationEvent.getWalletPublicKey(), incomingMoneyNotificationEvent.getAmount(), null, incomingMoneyNotificationEvent.getActorId(), incomingMoneyNotificationEvent.getActorType());
            }


            //this.notificationManager.recordNavigationStructure(xmlText,link,filename,skinId);

        }
}
