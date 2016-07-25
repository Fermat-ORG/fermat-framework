package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerClosePurchaseNegotiationTransaction;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerClosePurchaseNegotiationTransaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Yordin Alayn on 02.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReceivePurchaseNegotiationTranasctionTest {

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.CUSTOMER_BROKER_PURCHASE)
    @Mock
    private CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation;

    private final UUID transactionId = UUID.randomUUID();

    @Test
    public void receivePurchaseNegotiationTranasction() throws Exception {

        CustomerBrokerClosePurchaseNegotiationTransaction customerBrokerClosePurchaseNegotiationTransaction = mock(CustomerBrokerClosePurchaseNegotiationTransaction.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(customerBrokerClosePurchaseNegotiationTransaction).receivePurchaseNegotiationTranasction(transactionId, customerBrokerPurchaseNegotiation);

    }

}
