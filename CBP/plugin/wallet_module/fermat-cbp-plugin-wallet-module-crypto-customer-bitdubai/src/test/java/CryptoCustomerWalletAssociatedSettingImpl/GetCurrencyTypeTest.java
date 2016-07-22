package CryptoCustomerWalletAssociatedSettingImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletAssociatedSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 6/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetCurrencyTypeTest {
    @Test
    public void getCurrencyType() {
        CryptoCustomerWalletAssociatedSettingImpl cryptoCustomerWalletAssociatedSetting = mock(CryptoCustomerWalletAssociatedSettingImpl.class);
        when(cryptoCustomerWalletAssociatedSetting.getCurrencyType()).thenReturn(CurrencyType.BANK_MONEY);
        assertThat(cryptoCustomerWalletAssociatedSetting.getCurrencyType()).isNotNull();
    }
}

