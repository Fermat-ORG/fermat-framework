package CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCryptoCustomerWalletManager;

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
public class GetSelectedActorIdentityTest {
    @Test
    public void getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        CryptoCustomerWalletModuleCryptoCustomerWalletManager cryptoCustomerWalletModuleCryptoCustomerWalletManager = mock(CryptoCustomerWalletModuleCryptoCustomerWalletManager.class);
        when(cryptoCustomerWalletModuleCryptoCustomerWalletManager.getSelectedActorIdentity()).thenReturn(new ActiveActorIdentityInformation() {
            @Override
            public String getPublicKey() {
                return null;
            }

            @Override
            public Actors getActorType() {
                return null;
            }

            @Override
            public String getAlias() {
                return null;
            }

            @Override
            public byte[] getImage() {
                return new byte[0];
            }
        });
        assertThat(cryptoCustomerWalletModuleCryptoCustomerWalletManager.getSelectedActorIdentity()).isNotNull();
    }

}
