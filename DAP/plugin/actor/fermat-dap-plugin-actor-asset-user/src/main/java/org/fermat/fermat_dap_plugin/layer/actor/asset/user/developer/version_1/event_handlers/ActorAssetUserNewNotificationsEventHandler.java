package org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.exceptions.UnexpectedEventException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantGetActorAssetNotificationException;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.AssetUserActorPluginRoot;

/**
 * Created by Nerio on 17/02/16.
 */
public class ActorAssetUserNewNotificationsEventHandler implements FermatEventHandler {

    AssetUserActorPluginRoot assetActorUserPluginRoot;

    FermatEventMonitor fermatEventMonitor;

    public ActorAssetUserNewNotificationsEventHandler(AssetUserActorPluginRoot assetActorUserPluginRoot) {
        this.assetActorUserPluginRoot = assetActorUserPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (this.assetActorUserPluginRoot.getStatus() == ServiceStatus.STARTED) {
            if (fermatEvent.getSource() == EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER) {
                System.out.println("ACTOR ASSET USER - HANDLER NOTIFICACIONES PENDIENTES A ACTOR ASSET USER!!!");
                try {
                    assetActorUserPluginRoot.processNotifications();
                } catch (CantGetActorAssetNotificationException e) {
                    this.fermatEventMonitor.handleEventException(e, fermatEvent);
                } catch (Exception e) {
                    this.fermatEventMonitor.handleEventException(e, fermatEvent);
                }
            }
        } else {
            EventType eventExpected = EventType.ACTOR_ASSET_NETWORK_SERVICE_NEW_NOTIFICATIONS;
            EventSource eventSource = EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER;
            String context = "Event received: " + fermatEvent.getEventType().toString() + " - " + fermatEvent.getEventType().getCode() + "\n" +
                    "Event Source received: " + fermatEvent.getSource().toString() + " - " + fermatEvent.getSource().getCode() + "\n" +
                    "Event expected: " + eventExpected.toString() + " - " + eventExpected.getCode() + "\n" +
                    "Event Source: " + eventSource.toString() + " - " + eventSource.getCode();
            throw new UnexpectedEventException(context);
        }
    }
}
