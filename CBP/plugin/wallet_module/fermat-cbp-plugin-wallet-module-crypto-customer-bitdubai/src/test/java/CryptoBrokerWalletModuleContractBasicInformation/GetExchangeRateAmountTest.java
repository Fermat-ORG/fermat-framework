package CryptoBrokerWalletModuleContractBasicInformation;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleContractBasicInformation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 5/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetExchangeRateAmountTest {
    @Test
    public void getExchangeRateAmount() {
        CryptoBrokerWalletModuleContractBasicInformation cryptoBrokerWalletModuleContractBasicInformation = mock(CryptoBrokerWalletModuleContractBasicInformation.class);
        when(cryptoBrokerWalletModuleContractBasicInformation.getExchangeRateAmount()).thenReturn(10.0f);
        assertThat(cryptoBrokerWalletModuleContractBasicInformation.getExchangeRateAmount()).isNotNull();
    }
}
