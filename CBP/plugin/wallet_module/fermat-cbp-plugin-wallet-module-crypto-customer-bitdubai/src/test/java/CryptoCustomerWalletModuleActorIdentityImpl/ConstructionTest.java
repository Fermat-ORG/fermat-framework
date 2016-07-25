package CryptoCustomerWalletModuleActorIdentityImpl;

import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleActorIdentityImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Roy on 6/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    private String alias = new String();
    private byte[] img = new byte[0];

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {

        CryptoCustomerWalletModuleActorIdentityImpl cryptoCustomerWalletModuleActorIdentity = new CryptoCustomerWalletModuleActorIdentityImpl(
                this.alias,
                this.img
        );
        assertThat(cryptoCustomerWalletModuleActorIdentity).isNotNull();
    }
}
