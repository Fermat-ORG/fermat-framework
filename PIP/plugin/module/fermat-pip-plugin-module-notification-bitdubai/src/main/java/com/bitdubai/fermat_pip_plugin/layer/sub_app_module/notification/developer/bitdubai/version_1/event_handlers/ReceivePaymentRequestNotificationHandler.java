package com.bitdubai.fermat_pip_plugin.layer.sub_app_module.notification.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.module.notification.interfaces.NotificationManagerMiddleware;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.ReceivePaymentRequestNotificationEvent;

/**
 * Created by natalia on 30/12/15.
 */
public class ReceivePaymentRequestNotificationHandler implements FermatEventHandler {

    NotificationManagerMiddleware notificationManager;

    public ReceivePaymentRequestNotificationHandler(final NotificationManagerMiddleware notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        ReceivePaymentRequestNotificationEvent receivePaymentRequestNotificationEvent =(ReceivePaymentRequestNotificationEvent) fermatEvent;


        if (((Service) this.notificationManager).getStatus() == ServiceStatus.STARTED) {

            notificationManager.addReceiveRequestPaymentNotification(receivePaymentRequestNotificationEvent.getSource(), receivePaymentRequestNotificationEvent.getCryptoCurrency(),receivePaymentRequestNotificationEvent.getAmount());
        }


    }
}
