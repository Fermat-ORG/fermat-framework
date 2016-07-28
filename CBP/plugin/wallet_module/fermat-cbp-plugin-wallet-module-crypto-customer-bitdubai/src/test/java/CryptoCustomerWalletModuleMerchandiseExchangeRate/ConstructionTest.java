package CryptoCustomerWalletModuleMerchandiseExchangeRate;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleMerchandiseExchangeRate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 9/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    private FermatEnum merchandise = new FermatEnum() {
        @Override
        public String getCode() {
            return null;
        }
    };
    private FermatEnum payment = new FermatEnum() {
        @Override
        public String getCode() {
            return null;
        }
    };
    private double exchangeRate = 12.0;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoCustomerWalletModuleMerchandiseExchangeRate cryptoCustomerWalletModuleMerchandiseExchangeRate = new CryptoCustomerWalletModuleMerchandiseExchangeRate(
                this.merchandise,
                this.payment,
                this.exchangeRate
        );
        assertThat(cryptoCustomerWalletModuleMerchandiseExchangeRate).isNotNull();
    }
}
