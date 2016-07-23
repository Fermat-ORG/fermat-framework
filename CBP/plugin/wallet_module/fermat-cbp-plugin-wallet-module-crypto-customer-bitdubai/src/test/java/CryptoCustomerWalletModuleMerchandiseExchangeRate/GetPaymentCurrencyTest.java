package CryptoCustomerWalletModuleMerchandiseExchangeRate;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleMerchandiseExchangeRate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetPaymentCurrencyTest {
    @Test
    public void getPaymentCurrency() {
        CryptoCustomerWalletModuleMerchandiseExchangeRate cryptoCustomerWalletModuleMerchandiseExchangeRate = mock(CryptoCustomerWalletModuleMerchandiseExchangeRate.class);
        when(cryptoCustomerWalletModuleMerchandiseExchangeRate.getPaymentCurrency()).thenReturn(new FermatEnum() {
            @Override
            public String getCode() {
                return null;
            }
        });
        assertThat(cryptoCustomerWalletModuleMerchandiseExchangeRate.getPaymentCurrency()).isNotNull();
    }
}
