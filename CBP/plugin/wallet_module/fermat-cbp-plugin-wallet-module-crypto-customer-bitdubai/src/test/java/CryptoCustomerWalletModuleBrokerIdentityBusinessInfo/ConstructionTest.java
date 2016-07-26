package CryptoCustomerWalletModuleBrokerIdentityBusinessInfo;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleBrokerIdentityBusinessInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 6/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    private String publickey = new String();
    private String alias = new String();
    private byte[] img = new byte[0];
    private FermatEnum merchandiseCurrency = new FermatEnum() {
        @Override
        public String getCode() {
            return null;
        }
    };

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoCustomerWalletModuleBrokerIdentityBusinessInfo cryptoCustomerWalletModuleBrokerIdentityBusinessInfo = new CryptoCustomerWalletModuleBrokerIdentityBusinessInfo(
                this.alias,
                this.img,
                this.publickey,
                this.merchandiseCurrency
        );
        assertThat(cryptoCustomerWalletModuleBrokerIdentityBusinessInfo).isNotNull();
    }
}
