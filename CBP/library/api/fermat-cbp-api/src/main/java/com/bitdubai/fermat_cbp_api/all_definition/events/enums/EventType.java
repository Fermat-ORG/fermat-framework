package com.bitdubai.fermat_cbp_api.all_definition.events.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionRequestNewsEvent;
import com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.events.CryptoBrokerConnectionRequestUpdatesEvent;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingBusinessTransactionContractHash;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingConfirmBusinessTransactionContract;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingConfirmBusinessTransactionResponse;
import com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.events.IncomingNewContractStatusUpdate;

/**
 * The enum <code>com.bitdubai.fermat_cbp_api.fermat_cbp_api.events.enums.EventType</code>
 * represent the different type of events found on cbp platform.<p/>
 * <p/>
 * Created by lnacosta (laion.cj91@gmail.com) on 17/11/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum EventType implements FermatEventEnum {

    /**
     * Please for doing the code more readable, keep the elements of the enum ordered.
     */
    CRYPTO_BROKER_CONNECTION_REQUEST_NEWS("CBCRNWS") {
        public final FermatEvent getNewEvent() { return new CryptoBrokerConnectionRequestNewsEvent(this); }
    },
    CRYPTO_BROKER_CONNECTION_REQUEST_UPDATES("CBCRUPD") {
        public final FermatEvent getNewEvent() { return new CryptoBrokerConnectionRequestUpdatesEvent(this); }
    },
    INCOMING_BUSINESS_TRANSACTION_CONTRACT_HASH("IBTCH") {
        public final FermatEvent getNewEvent() { return new IncomingBusinessTransactionContractHash(this);}
    },
    INCOMING_CONFIRM_BUSINESS_TRANSACTION_CONTRACT("ICBTC") {
        public final FermatEvent getNewEvent() { return new IncomingConfirmBusinessTransactionContract(this);}
    },
    INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE("ICBTR") {
        public final FermatEvent getNewEvent() { return new IncomingConfirmBusinessTransactionResponse(this);}
    },
    INCOMING_NEW_CONTRACT_STATUS_UPDATE("INCSU") {
        public final FermatEvent getNewEvent() { return new IncomingNewContractStatusUpdate(this);}
    }

    ;

    private final String code;

    EventType(String code) {
        this.code = code;
    }

    @Override // by default
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }

    @Override
    public final String getCode() {
        return this.code;
    }

    @Override
    public final Platforms getPlatform() {
        return Platforms.CRYPTO_BROKER_PLATFORM;
    }

}
