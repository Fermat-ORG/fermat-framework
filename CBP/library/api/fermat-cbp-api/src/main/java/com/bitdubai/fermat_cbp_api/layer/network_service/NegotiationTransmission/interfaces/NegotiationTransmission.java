package com.bitdubai.fermat_cbp_api.layer.network_service.NegotiationTransmission.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 29.11.15.
 */
public interface NegotiationTransmission {

    UUID getTransmissionId();

    UUID getTransactionId();

    UUID getNegotiationId();

    NegotiationTransactionType getNegotiationTansactionType();

    String getPublicKeyActorSend();

    PlatformComponentType getActorSendType();

    String getPublicKeyActorReceive();

    PlatformComponentType getActorReceiveType();

    NegotiationTransmissionType getTransmissionType();

    NegotiationTransmissionState getTransmissionState();


    long getTimestamp();

}
