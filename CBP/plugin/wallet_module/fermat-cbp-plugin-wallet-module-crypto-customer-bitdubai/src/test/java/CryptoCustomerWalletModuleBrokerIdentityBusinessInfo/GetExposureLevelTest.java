package CryptoCustomerWalletModuleBrokerIdentityBusinessInfo;

import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleBrokerIdentityBusinessInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 6/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetExposureLevelTest {
    @Test
    public void getExposureLevel() {
        CryptoCustomerWalletModuleBrokerIdentityBusinessInfo cryptoCustomerWalletModuleBrokerIdentityBusinessInfo = mock(CryptoCustomerWalletModuleBrokerIdentityBusinessInfo.class);
        when(cryptoCustomerWalletModuleBrokerIdentityBusinessInfo.getExposureLevel()).thenReturn(ExposureLevel.PUBLISH);
        assertThat(cryptoCustomerWalletModuleBrokerIdentityBusinessInfo.getExposureLevel()).isNotNull();
    }
}
