package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_community.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.all_definition.enums.EventType;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_community.interfaces.WalletCommunityManager;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_community.developer.bitdubai.version_1.event_handlers.FinishedWalletInstallationEventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loui on 17/02/15.
 */

/**
 * This Plugin maintains a list of Users for each wallet installed. I will include a pre-configured number of users per
 * wallet type, prioritizing the ones which location is closer to the users average location.
 * 
 * 
 * It also maintains a general list of system users also by average location as the main community.
 *
 * * * * 
 */

public class WalletCommunityNetworkServicePluginRoot extends AbstractPlugin implements
        WalletCommunityManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER         )
    private EventManager eventManager;


    List<FermatEventListener> listenersAdded = new ArrayList<>();

    public WalletCommunityNetworkServicePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * Service Interface implementation.
     */

    @Override
    public void start()  throws CantStartPluginException {
        /**
         * I will initialize the handling of platform events.
         */

        FermatEventListener fermatEventListener;
        FermatEventHandler fermatEventHandler;

        fermatEventListener = eventManager.getNewListener(EventType.FINISHED_WALLET_INSTALLATION);
        fermatEventHandler = new FinishedWalletInstallationEventHandler();
        ((FinishedWalletInstallationEventHandler) fermatEventHandler).setWalletCommunityManager(this);
        fermatEventListener.setEventHandler(fermatEventHandler);
        eventManager.addListener(fermatEventListener);
        listenersAdded.add(fermatEventListener);

         /*
         * Listen and handle VPN Connection Close Notification Event
         */
     //   fermatEventListener = eventManager.getNewListener(P2pEventType.VPN_CONNECTION_CLOSE);
     //   fermatEventListener.setEventHandler(new VPNConnectionCloseNotificationEventHandler(this));
      //  eventManager.addListener(fermatEventListener);
      //  listenersAdded.add(fermatEventListener);

              /*
         * Listen and handle Client Connection Close Notification Event
         */
      //  fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_CLOSE);
      //  fermatEventListener.setEventHandler(new ClientConnectionCloseNotificationEventHandler(this));
      //  eventManager.addListener(fermatEventListener);
      //  listenersAdded.add(fermatEventListener);

          /*
         * Listen and handle Client Connection Loose Notification Event
         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_CONNECTION_LOOSE);
//        fermatEventListener.setEventHandler(new ClientConnectionLooseNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);


        /*
         * Listen and handle Client Connection Success Reconnect Notification Event
         */
//        fermatEventListener = eventManager.getNewListener(P2pEventType.CLIENT_SUCCESS_RECONNECT);
//        fermatEventListener.setEventHandler(new ClientSuccessfulReconnectNotificationEventHandler(this));
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);


        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        /**
         * I will remove all the event listeners registered with the event manager.
         */

        for (FermatEventListener fermatEventListener : listenersAdded) {
            eventManager.removeListener(fermatEventListener);
        }

        listenersAdded.clear();
        this.serviceStatus = ServiceStatus.STOPPED;

    }

}
