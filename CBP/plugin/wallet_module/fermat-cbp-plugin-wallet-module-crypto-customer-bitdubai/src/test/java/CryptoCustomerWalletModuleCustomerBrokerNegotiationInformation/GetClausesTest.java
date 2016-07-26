package CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetClausesTest {
    @Test
    public void getClauses() {
        CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = mock(CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.class);
        when(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.getClauses()).thenReturn(new Map<ClauseType, ClauseInformation>() {
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
            public ClauseInformation get(Object key) {
                return null;
            }

            @Override
            public ClauseInformation put(ClauseType key, ClauseInformation value) {
                return null;
            }

            @Override
            public ClauseInformation remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends ClauseType, ? extends ClauseInformation> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<ClauseType> keySet() {
                return null;
            }

            @Override
            public Collection<ClauseInformation> values() {
                return null;
            }

            @Override
            public Set<Entry<ClauseType, ClauseInformation>> entrySet() {
                return null;
            }
        });
        assertThat(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.getClauses()).isNotNull();
    }
}
