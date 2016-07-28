package EmptyCustomerBrokerNegotiationInformationImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.EmptyCustomerBrokerNegotiationInformationImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PutClauseTest {
    @Test
    public void putClause() {
        EmptyCustomerBrokerNegotiationInformationImpl emptyCustomerBrokerNegotiationInformation = mock(EmptyCustomerBrokerNegotiationInformationImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(emptyCustomerBrokerNegotiationInformation).putClause(Mockito.any(ClauseType.class), Mockito.any(String.class));
        doCallRealMethod().when(emptyCustomerBrokerNegotiationInformation).putClause(Mockito.any(ClauseInformation.class), Mockito.any(String.class));
    }
}
