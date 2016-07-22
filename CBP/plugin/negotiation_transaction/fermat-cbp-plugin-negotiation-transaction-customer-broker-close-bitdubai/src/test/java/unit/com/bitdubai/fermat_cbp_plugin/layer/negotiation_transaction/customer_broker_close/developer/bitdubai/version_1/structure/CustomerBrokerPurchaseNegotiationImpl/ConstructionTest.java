package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseNegotiationImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseNegotiationImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 02.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private final UUID negotiationId = UUID.randomUUID();
    ;

    private final String publicKeyCustomer = "publicKeyCustomer";

    private final String publicKeyBroker = "publicKeyBroker";

    private final long startDataTime = 0;

    private final long negotiationExpirationDate = 0;

    private final NegotiationStatus statusNegotiation = NegotiationStatus.SENT_TO_BROKER;

    private final Collection<Clause> clauses = null;

    private CustomerBrokerPurchaseNegotiationImpl testObj1;

    @Before
    public void setUp() {
        testObj1 = new CustomerBrokerPurchaseNegotiationImpl(
                negotiationId,
                publicKeyCustomer,
                publicKeyBroker,
                startDataTime,
                negotiationExpirationDate,
                statusNegotiation,
                clauses
        );
    }

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        System.out.print("\n* Construction_ValidParameters_NewObjectCreated");
        assertThat(testObj1).isNotNull();
    }
}
