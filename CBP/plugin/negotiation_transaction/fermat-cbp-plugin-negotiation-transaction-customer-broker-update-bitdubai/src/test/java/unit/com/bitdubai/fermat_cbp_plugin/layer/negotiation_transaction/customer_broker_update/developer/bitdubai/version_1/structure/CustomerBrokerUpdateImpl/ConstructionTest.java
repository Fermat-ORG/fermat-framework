package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure.CustomerBrokerUpdateImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.structure.CustomerBrokerUpdateImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 01.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private final Date time = new Date();

    private final UUID transactionId = UUID.randomUUID();

    private final UUID negotiationId = UUID.randomUUID();

    private final String publicKeyBroker = "publicKeyBroker";

    private final String publicKeyCustomer = "publicKeyCustomer";

    private final NegotiationTransactionStatus negotiationTransactionStatus = NegotiationTransactionStatus.PENDING_SUBMIT;

    private final NegotiationType negotiationType = NegotiationType.PURCHASE;

    private final String negotiationXML = "negotiationXML";

    private final long timestamp = time.getTime();

    private CustomerBrokerUpdateImpl testObj1;

    @Before
    public void setUp() {
        testObj1 = new CustomerBrokerUpdateImpl(
                transactionId,
                negotiationId,
                publicKeyBroker,
                publicKeyCustomer,
                negotiationTransactionStatus,
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
