package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerNegotiationClauseImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerNegotiationClauseImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 02.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private final UUID clauseId = UUID.randomUUID();

    private final ClauseType type = ClauseType.BROKER_CRYPTO_ADDRESS;

    private final String value = "value";

    private final ClauseStatus status = ClauseStatus.AGREED;

    private final String proposedBy = "proposedBy";

    private final short indexOrder = 1;

    private CustomerBrokerNegotiationClauseImpl testObj1;

    @Before
    public void setUp() {
        testObj1 = new CustomerBrokerNegotiationClauseImpl(
                clauseId,
                type,
                value,
                status,
                proposedBy,
                indexOrder
        );
    }

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        System.out.print("\n* Construction_ValidParameters_NewObjectCreated");
        assertThat(testObj1).isNotNull();
    }
}
