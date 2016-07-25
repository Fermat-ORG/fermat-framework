package CryptoCustomerWalletAssociatedSettingImpl;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletAssociatedSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by roy on 6/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetCurrencyTypeTest {
    @Test
    public void setCurrencyType() {
        CryptoCustomerWalletAssociatedSettingImpl cryptoCustomerWalletAssociatedSetting = mock(CryptoCustomerWalletAssociatedSettingImpl.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoCustomerWalletAssociatedSetting).setCurrencyType(Mockito.any(CurrencyType.class));
    }
}
