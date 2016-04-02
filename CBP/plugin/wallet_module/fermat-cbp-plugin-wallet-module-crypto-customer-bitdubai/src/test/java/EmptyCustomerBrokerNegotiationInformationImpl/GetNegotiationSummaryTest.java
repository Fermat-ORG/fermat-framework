package EmptyCustomerBrokerNegotiationInformationImpl;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.EmptyCustomerBrokerNegotiationInformationImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetNegotiationSummaryTest {
    @Test
    public void getNegotiationSummary() {
        EmptyCustomerBrokerNegotiationInformationImpl emptyCustomerBrokerNegotiationInformation = mock(EmptyCustomerBrokerNegotiationInformationImpl.class);
        when(emptyCustomerBrokerNegotiationInformation.getNegotiationSummary()).thenReturn(new Map<ClauseType, String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public String get(Object key) {
                return null;
            }

            @Override
            public String put(ClauseType key, String value) {
                return null;
            }

            @Override
            public String remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends ClauseType, ? extends String> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<ClauseType> keySet() {
                return null;
            }

            @Override
            public Collection<String> values() {
                return null;
            }

            @Override
            public Set<Entry<ClauseType, String>> entrySet() {
                return null;
            }
        });
        assertThat(emptyCustomerBrokerNegotiationInformation.getNegotiationSummary()).isNotNull();
    }
}
