package CryptoCustomerWalletModuleBrokerIdentityBusinessInfo;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleBrokerIdentityBusinessInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 6/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetMerchandiseCurrencyTest {
    @Test
    public void getMerchandiseCurrency() {
        CryptoCustomerWalletModuleBrokerIdentityBusinessInfo cryptoCustomerWalletModuleBrokerIdentityBusinessInfo = mock(CryptoCustomerWalletModuleBrokerIdentityBusinessInfo.class);
        when(cryptoCustomerWalletModuleBrokerIdentityBusinessInfo.getMerchandiseCurrency()).thenReturn(new FermatEnum() {
            @Override
            public String getCode() {
                return null;
            }
        });
        assertThat(cryptoCustomerWalletModuleBrokerIdentityBusinessInfo.getMerchandiseCurrency()).isNotNull();
    }
}
