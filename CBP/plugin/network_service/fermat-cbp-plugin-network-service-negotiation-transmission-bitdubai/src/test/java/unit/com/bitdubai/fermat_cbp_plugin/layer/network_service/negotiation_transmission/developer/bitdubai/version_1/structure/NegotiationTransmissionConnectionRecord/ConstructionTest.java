package unit.com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionConnectionRecord;

import com.bitdubai.fermat_cbp_plugin.layer.network_service.negotiation_transmission.developer.bitdubai.version_1.structure.NegotiationTransmissionConnectionRecord;

import org.junit.Before;
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

    private final UUID id = UUID.randomUUID();

    private final String actorPublicKey = "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725";

    private final String actorPublicKey2 = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B235";

    private final String ipkNetworkService = "ipkNetworkService";

    private final String ipkNetworkService2 = "ipkNetworkService2";

    private final String lastConnection = "301215";

    private NegotiationTransmissionConnectionRecord testObj1;

    @Before
    public void setUp() {
        testObj1 = new NegotiationTransmissionConnectionRecord(
                id,
                actorPublicKey,
                ipkNetworkService,
                lastConnection
        );
    }

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        System.out.print("\n* Construction_ValidParameters_NewObjectCreated");
        assertThat(testObj1).isNotNull();
    }
}