package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure.CustomerBrokerNewSaleNegotiationTransaction;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_new.developer.bitdubai.version_1.structure.CustomerBrokerNewSaleNegotiationTransaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Yordin Alayn on 02.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SendSaleNegotiationTranasctionTest {

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.CUSTOMER_BROKER_SALE)
    @Mock
    private CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation;

    @Test
    public void sendSaleNegotiationTranasction() throws Exception {

        CustomerBrokerNewSaleNegotiationTransaction customerBrokerNewSaleNegotiationTransaction = mock(CustomerBrokerNewSaleNegotiationTransaction.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(customerBrokerNewSaleNegotiationTransaction).sendSaleNegotiationTranasction(customerBrokerSaleNegotiation);

    }
}
