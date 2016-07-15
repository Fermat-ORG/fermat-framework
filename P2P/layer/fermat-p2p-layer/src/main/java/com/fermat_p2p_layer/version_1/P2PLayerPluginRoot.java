package com.fermat_p2p_layer.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.events.NetworkClientNewMessageTransmitEvent;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.exceptions.CantRegisterProfileException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.NetworkChannel;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces.P2PLayerManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractNetworkService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Matias Furszyfer on 2016.07.06..
 */
public class P2PLayerPluginRoot extends AbstractPlugin implements P2PLayerManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;


    private Map<String,AbstractNetworkService> networkServices;
    private NetworkChannel client;

    private List<FermatEventListener> listenersAdded;


    public P2PLayerPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {
        networkServices = new HashMap<>();
        this.listenersAdded        = new CopyOnWriteArrayList<>();


//        FermatEventListener fermatEventListener = eventManager.getNewListener(P2pEventType.NETWORK_CLIENT_NEW_MESSAGE_TRANSMIT);
//        fermatEventListener.setEventHandler(new FermatEventHandler() {
//            @Override
//            public void handleEvent(FermatEvent fermatEvent) throws FermatException {
//                distributeMessage(NetworkClientNewMessageTransmitEvent)
//            }
//        });
//        eventManager.addListener(fermatEventListener);
//        listenersAdded.add(fermatEventListener);

        super.start();
    }


    private void distributeMessage(String networkType,NetworkClientNewMessageTransmitEvent fermatEvent){
        if(networkServices.containsKey(networkType)){
            networkServices.get(networkType).onMessageReceived(fermatEvent.getContent());
        }
    }

    @Override
    public void register(AbstractNetworkService abstractNetworkService) {
        networkServices.put(abstractNetworkService.getNetworkServiceType().getCode(), abstractNetworkService);
    }

    @Override
    public void register(NetworkChannel NetworkChannel) {
        if(client!=null) throw new IllegalArgumentException("Client already registered");
        client = NetworkChannel;
        client.connect();
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (client.isConnected()) {
                    for (AbstractNetworkService abstractNetworkService : networkServices.values()) {
                        try {
                            abstractNetworkService.startConnection();
                        } catch (CantRegisterProfileException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        scheduledExecutorService.shutdownNow();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 5, 5, TimeUnit.SECONDS);
    }



}
