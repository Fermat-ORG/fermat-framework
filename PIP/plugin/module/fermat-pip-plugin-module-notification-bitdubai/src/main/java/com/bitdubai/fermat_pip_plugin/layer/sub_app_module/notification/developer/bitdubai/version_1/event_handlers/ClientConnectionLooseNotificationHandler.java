package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.dmp_module.notification.NotificationType;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.events.ClientConnectionLooseNotificationEvent;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationManagerMiddleware;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */
public class ClientConnectionLooseNotificationHandler implements FermatEventHandler {


    NotificationManagerMiddleware notificationManager;

    public ClientConnectionLooseNotificationHandler(final NotificationManagerMiddleware notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        ClientConnectionLooseNotificationEvent clientConnectionLooseNotificationEvent =(ClientConnectionLooseNotificationEvent) fermatEvent;

        //TODO: falta ver si le seteo la activity (enum de las activities) o/y si le setio la public Key de la wallet


        System.out.println("PROBANDO EVENTO MATI, PARA NOTIFICACIONES");

        if (((Service) this.notificationManager).getStatus() == ServiceStatus.STARTED) {

            System.out.println("PROBANDO EVENTO MATI, PARA NOTIFICACIONES 2");
            //TODO: acá hay que implementar el add al pool de notificaciones
            notificationManager.addNotificacion(NotificationType.CLOUD_CLIENT_CONNECTION_LOOSE);

        }

            //this.notificationManager.recordNavigationStructure(xmlText,link,filename,skinId);

    }
}
