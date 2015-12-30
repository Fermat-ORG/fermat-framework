package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionConnectionRecord;

import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionConnectionRecord;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 30.12.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private UUID    id                  = UUID.randomUUID();

    private String  actorPublicKey      = "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725";;

    private String  ipkNetworkService   = "ipkNetworkService";

    private String  lastConnection      = "301215";

    private NegotiationTransmissionConnectionRecord testMonitorAgent;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {

        testMonitorAgent = new NegotiationTransmissionConnectionRecord(
            id,
            actorPublicKey,
            ipkNetworkService,
            lastConnection
        );
        assertThat(testMonitorAgent).isNotNull();

    }
}