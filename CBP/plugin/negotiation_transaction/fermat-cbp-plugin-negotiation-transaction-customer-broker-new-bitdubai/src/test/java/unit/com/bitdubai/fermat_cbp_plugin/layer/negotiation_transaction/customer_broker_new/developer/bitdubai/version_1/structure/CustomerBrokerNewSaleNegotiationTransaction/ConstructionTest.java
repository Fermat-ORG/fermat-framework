package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure.CustomerBrokerNewSaleNegotiationTransaction;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.database.CustomerBrokerNewNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure.CustomerBrokerNewSaleNegotiationTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 01.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.CUSTOMER_BROKER_SALE)
    @Mock
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @Mock
    private CustomerBrokerNewNegotiationTransactionDatabaseDao customerBrokerNewNegotiationTransactionDatabaseDao;

    private CustomerBrokerNewSaleNegotiationTransaction testObj1;

    @Before
    public void setUp() {
        testObj1 = new CustomerBrokerNewSaleNegotiationTransaction(
                customerBrokerSaleNegotiationManager,
                customerBrokerNewNegotiationTransactionDatabaseDao
        );
    }

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        System.out.print("\n* Construction_ValidParameters_NewObjectCreated");
        assertThat(testObj1).isNotNull();
    }
}