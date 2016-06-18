package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.DeniedPaymentRequestNotificationEvent;

/**
 * Created by natalia on 30/12/15.
 */
public class DeniedPaymentRequestNotificationHandler implements FermatEventHandler {

    NotificationManagerMiddleware notificationManager;

    public DeniedPaymentRequestNotificationHandler(final NotificationManagerMiddleware notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        DeniedPaymentRequestNotificationEvent deniedPaymentRequestNotificationEvent = (DeniedPaymentRequestNotificationEvent) fermatEvent;


        if (((Service) this.notificationManager).getStatus() == ServiceStatus.STARTED) {

            notificationManager.addDeniedRequestPaymentNotification(deniedPaymentRequestNotificationEvent.getSource(), deniedPaymentRequestNotificationEvent.getCryptoCurrency(), deniedPaymentRequestNotificationEvent.getAmount());
        }


    }
}
