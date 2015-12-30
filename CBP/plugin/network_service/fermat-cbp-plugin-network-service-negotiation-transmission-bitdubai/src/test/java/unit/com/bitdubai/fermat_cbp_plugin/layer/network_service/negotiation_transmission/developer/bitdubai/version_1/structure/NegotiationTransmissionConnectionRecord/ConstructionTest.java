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

    private UUID    id                  = UUID.randomUUID();

    private String  actorPublicKey      = "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725";

    private String  actorPublicKey2     = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B235";

    private String  ipkNetworkService   = "ipkNetworkService";

    private String  ipkNetworkService2  = "ipkNetworkService2";

    private String  lastConnection      = "301215";

    private NegotiationTransmissionConnectionRecord testObj1, testObj2;

    @Before
    public void setUp(){
        System.out.print("\n*** UNNIT TESTS: NegotiationTransmissionConnectionRecord - ConstructionTest");
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

    @Test
    public void Equals_SameValues_True(){
        System.out.print("\n* Equals_SameValues_True");
        testObj2 = new NegotiationTransmissionConnectionRecord(
                id,
                actorPublicKey,
                ipkNetworkService,
                lastConnection
        );
        assertThat(testObj1).isEqualTo(testObj1);
        assertThat(testObj1.hashCode()).isEqualTo(testObj1.hashCode());
    }

    @Test
    public void Equals_DifferentActorPublicKey_False(){
        System.out.print("\n* Equals_DifferentActorPublicKey_False");
        testObj2 = new NegotiationTransmissionConnectionRecord(
                id,
                actorPublicKey2,
                ipkNetworkService,
                lastConnection
        );
        assertThat(testObj1).isNotEqualTo(testObj2);
        assertThat(testObj1.hashCode()).isNotEqualTo(testObj2.hashCode());
    }

    @Test
    public void Equals_DifferentIpkNetworkService_False(){
        System.out.print("\n* Equals_DifferentIpkNetworkService_False");
        testObj2 = new NegotiationTransmissionConnectionRecord(
                id,
                actorPublicKey,
                ipkNetworkService2,
                lastConnection
        );
        assertThat(testObj1).isNotEqualTo(testObj2);
        assertThat(testObj1.hashCode()).isNotEqualTo(testObj2.hashCode());
    }
}