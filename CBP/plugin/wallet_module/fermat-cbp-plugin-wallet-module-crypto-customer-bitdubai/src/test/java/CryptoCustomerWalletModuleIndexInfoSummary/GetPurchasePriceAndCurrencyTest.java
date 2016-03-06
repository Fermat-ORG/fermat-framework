package CryptoCustomerWalletModuleIndexInfoSummary;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_api.layer.world.interfaces.Index;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleContractBasicInformation;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleIndexInfoSummary;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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
public class GetPurchasePriceAndCurrencyTest {
    @Test
    public void getPurchasePriceAndCurrency() {
        CryptoCustomerWalletModuleIndexInfoSummary cryptoCustomerWalletModuleIndexInfoSummary = mock(CryptoCustomerWalletModuleIndexInfoSummary.class);
        when(cryptoCustomerWalletModuleIndexInfoSummary.getSalePriceAndCurrency()).thenReturn(new String());
        assertThat(cryptoCustomerWalletModuleIndexInfoSummary.getSalePriceAndCurrency()).isNotNull();
    }
}
