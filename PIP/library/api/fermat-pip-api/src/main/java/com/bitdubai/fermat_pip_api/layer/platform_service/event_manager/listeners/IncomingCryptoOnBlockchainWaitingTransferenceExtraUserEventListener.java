package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;

/**
 * Created by rodrigo on 2015.07.08..
 */
public class IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEventListener extends com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener {

    public IncomingCryptoOnBlockchainWaitingTransferenceExtraUserEventListener(final FermatEventMonitor fermatEventMonitor){
        super(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_EXTRA_USER, fermatEventMonitor);
    }

}