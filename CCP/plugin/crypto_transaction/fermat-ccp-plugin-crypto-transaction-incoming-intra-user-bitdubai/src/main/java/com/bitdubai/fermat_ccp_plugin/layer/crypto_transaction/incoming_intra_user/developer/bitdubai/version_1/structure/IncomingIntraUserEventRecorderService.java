package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoMetadataEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.IncomingIntraUserCantSaveEventException;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * The class <code>IncomingIntraUserEventRecorderService</code>
 * is responsible to manage the event arrival from the different components that inform metadata reception
 * and crypto transaction receptions
 */
public class IncomingIntraUserEventRecorderService{
    private IncomingIntraUserRegistry incomingIntraUserRegistry;

    private EventManager        eventManager;
    private List<FermatEventListener> listenersRegistered = new ArrayList<>();

    private ServiceStatus serviceStatus;

    public IncomingIntraUserEventRecorderService(final EventManager eventManager, final IncomingIntraUserRegistry incomingIntraUserRegistry){
        this.eventManager              = eventManager;
        this.incomingIntraUserRegistry = incomingIntraUserRegistry;
        this.serviceStatus             = ServiceStatus.CREATED;
    }


    public void saveEvent(final FermatEvent event) throws IncomingIntraUserCantSaveEventException {
        if(serviceStatus.equals(ServiceStatus.STARTED))
            this.incomingIntraUserRegistry.saveNewEvent(event.getEventType(), event.getSource());
        else
            throw new IncomingIntraUserCantSaveEventException(IncomingIntraUserCantSaveEventException.DEFAULT_MESSAGE, null, null, "You must start the service first");
    }

    public void start() throws com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserEventRecorderServiceException {
        try {
            registerEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER, new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoOnBlockchainWaitingTransferenceIntraUserEventHandler(this));
            registerEvent(EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER, new IncomingCryptoOnCryptoNetworkWaitingTransferenceIntraUserEventHandler(this));
            registerEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_INTRA_USER, new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedOnBlockchainWaitingTransferenceIntraUserEventHandler(this));
            registerEvent(EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_INTRA_USER, new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceIntraUserEventHandler(this));
            registerEvent(com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType.INCOMING_CRYPTO_METADATA,new IncomingCryptoMetadataEventHandler(this));

            this.serviceStatus = ServiceStatus.STARTED;
        }catch (Exception e) {
            throw new com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserEventRecorderServiceException(com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_intra_user.developer.bitdubai.version_1.exceptions.CantStartIncomingIntraUserEventRecorderServiceException.DEFAULT_MESSAGE,FermatException.wrapException(e),"","");
        }
    }

    private void registerEvent(EventType eventType, FermatEventHandler fermatEventHandler){
        FermatEventListener fermatEventListener = this.eventManager.getNewListener(eventType);
        fermatEventListener.setEventHandler(fermatEventHandler);
        this.eventManager.addListener(fermatEventListener);
        this.listenersRegistered.add(fermatEventListener);
    }

    private void registerEvent(com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.enums.EventType eventType, FermatEventHandler fermatEventHandler){
        FermatEventListener fermatEventListener = this.eventManager.getNewListener(eventType);
        fermatEventListener.setEventHandler(fermatEventHandler);
        this.eventManager.addListener(fermatEventListener);
        this.listenersRegistered.add(fermatEventListener);
    }

    public void stop() {
        for (FermatEventListener fermatEventListener : listenersRegistered)
            this.eventManager.removeListener(fermatEventListener);
        this.listenersRegistered.clear();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    public ServiceStatus getServiceStatus() {
        return this.serviceStatus;
    }
}
