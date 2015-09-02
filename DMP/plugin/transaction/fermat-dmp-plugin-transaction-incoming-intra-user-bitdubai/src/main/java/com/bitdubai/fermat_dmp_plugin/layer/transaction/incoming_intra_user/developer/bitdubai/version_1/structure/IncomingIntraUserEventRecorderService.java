package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoMetadataEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEventHandler;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserEventRecorderServiceException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantSaveEventException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code></code>
 */
public class IncomingIntraUserEventRecorderService {

    private IncomingIntraUserRegistry incomingIntraUserRegistry;

    private EventManager eventManager;
    private List<EventListener> listenersRegistered = new ArrayList<>();

    private ServiceStatus serviceStatus;

    public IncomingIntraUserEventRecorderService(final EventManager eventManager, final IncomingIntraUserRegistry incomingIntraUserRegistry){
        this.eventManager = eventManager;
        this.incomingIntraUserRegistry = incomingIntraUserRegistry;
        this.serviceStatus = ServiceStatus.CREATED;
    }


    public void saveEvent(final PlatformEvent event) throws IncomingIntraUserCantSaveEventException {
        if(serviceStatus.equals(ServiceStatus.STARTED))
            this.incomingIntraUserRegistry.saveNewEvent(event.getEventType(), event.getSource());
        else
            throw new IncomingIntraUserCantSaveEventException(IncomingIntraUserCantSaveEventException.DEFAULT_MESSAGE, null, null, "You must start the service first");
    }

    public void start() throws CantStartIncomingIntraUserEventRecorderServiceException {
        try {
            registerEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER, new IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEventHandler(this));
            registerEvent(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER, new IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventHandler(this));
            registerEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER, new IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEventHandler(this));
            registerEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER, new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEventHandler(this));
            registerEvent(EventType.INCOMING_CRYPTO_METADATA,new IncomingCryptoMetadataEventHandler(this));

            this.serviceStatus = ServiceStatus.STARTED;
        }catch (Exception e) {
            throw new CantStartIncomingIntraUserEventRecorderServiceException(CantStartIncomingIntraUserEventRecorderServiceException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","");
        }
    }

    private void registerEvent(EventType eventType, EventHandler eventHandler){
        EventListener eventListener = this.eventManager.getNewListener(eventType);
        eventListener.setEventHandler(eventHandler);
        this.eventManager.addListener(eventListener);
        this.listenersRegistered.add(eventListener);
    }

    public void stop() {
        for (EventListener eventListener : listenersRegistered)
            this.eventManager.removeListener(eventListener);
        this.listenersRegistered.clear();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    public ServiceStatus getServiceStatus() {
        return this.serviceStatus;
    }
}
