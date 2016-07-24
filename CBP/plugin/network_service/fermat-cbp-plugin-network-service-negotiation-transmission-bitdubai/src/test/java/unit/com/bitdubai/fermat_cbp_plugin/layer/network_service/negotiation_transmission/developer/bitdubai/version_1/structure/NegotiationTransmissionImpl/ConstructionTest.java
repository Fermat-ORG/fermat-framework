package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionImpl;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionState;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransmissionType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 30.12.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private final Date time = new Date();

    private final UUID transmissionId = UUID.randomUUID();

    private final UUID transactionId = UUID.randomUUID();

    private final UUID negotiationId = UUID.randomUUID();

    private final NegotiationTransactionType negotiationTransactionType = NegotiationTransactionType.CUSTOMER_BROKER_NEW;

    private final NegotiationTransactionType negotiationTransactionType2 = NegotiationTransactionType.CUSTOMER_BROKER_UPDATE;

    private final String publicKeyActorSend = "publicKeyActorSend";

    private final PlatformComponentType actorSendType = PlatformComponentType.ACTOR_CRYPTO_CUSTOMER;

    private final String publicKeyActorReceive = "publicKeyActorReceive";

    private final PlatformComponentType actorReceiveType = PlatformComponentType.ACTOR_CRYPTO_BROKER;

    private final NegotiationTransmissionType transmissionType = NegotiationTransmissionType.TRANSMISSION_NEGOTIATION;

    private final NegotiationTransmissionState transmissionState = NegotiationTransmissionState.PROCESSING_SEND;

    private final NegotiationType negotiationType = NegotiationType.PURCHASE;

    private final String negotiationXML = "negotiationXML";

    private final long timestamp = time.getTime();

    private NegotiationTransmissionImpl testObj1;

    @Before
    public void setUp() {
        testObj1 = new NegotiationTransmissionImpl(
                transmissionId,
                transactionId,
                negotiationId,
                negotiationTransactionType,
                publicKeyActorSend,
                actorSendType,
                publicKeyActorReceive,
                actorReceiveType,
                transmissionType,
                transmissionState,
                negotiationType,
                negotiationXML,
                timestamp
        );
    }

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        System.out.print("\n* Construction_ValidParameters_NewObjectCreated");
        assertThat(testObj1).isNotNull();
    }
}