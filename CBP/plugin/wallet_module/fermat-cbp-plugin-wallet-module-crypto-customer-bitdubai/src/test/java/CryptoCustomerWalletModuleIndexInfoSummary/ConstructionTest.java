package CryptoCustomerWalletModuleIndexInfoSummary;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleIndexInfoSummary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    FermatEnum currency = new FermatEnum() {
        @Override
        public String getCode() {
            return null;
        }
    };
    FermatEnum referenceCurrency = new FermatEnum() {
        @Override
        public String getCode() {
            return null;
        }
    };
    double purchasePrice = 12.0;
    double salePrice = 12.0;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoCustomerWalletModuleIndexInfoSummary cryptoCustomerWalletModuleIndexInfoSummary = new CryptoCustomerWalletModuleIndexInfoSummary(
                this.currency,
                this.referenceCurrency,
                this.purchasePrice,
                this.salePrice
        );
        assertThat(cryptoCustomerWalletModuleIndexInfoSummary).isNotNull();
    }
}
