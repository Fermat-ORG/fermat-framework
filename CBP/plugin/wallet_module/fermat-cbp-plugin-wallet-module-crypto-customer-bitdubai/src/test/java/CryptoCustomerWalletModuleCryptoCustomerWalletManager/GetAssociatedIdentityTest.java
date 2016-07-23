package CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetAssociatedCryptoCustomerIdentityException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by roy on 8/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetAssociatedIdentityTest {
    @Test
    public void getAssociatedIdentity() throws CantGetAssociatedCryptoCustomerIdentityException {
        CryptoCustomerWalletModuleCryptoCustomerWalletManager cryptoCustomerWalletModuleCryptoCustomerWalletManager = mock(CryptoCustomerWalletModuleCryptoCustomerWalletManager.class);
        when(cryptoCustomerWalletModuleCryptoCustomerWalletManager.getAssociatedIdentity()).thenReturn(new CryptoCustomerIdentity() {
            @Override
            public String getAlias() {
                return null;
            }

            @Override
            public String getPublicKey() {
                return null;
            }

            @Override
            public byte[] getProfileImage() {
                return new byte[0];
            }

            @Override
            public void setNewProfileImage(byte[] imageBytes) {

            }

            @Override
            public boolean isPublished() {
                return false;
            }

            @Override
            public ExposureLevel getExposureLevel() {
                return null;
            }

            @Override
            public String createMessageSignature(String message) throws CantCreateMessageSignatureException {
                return null;
            }
        });
        assertThat(cryptoCustomerWalletModuleCryptoCustomerWalletManager.getAssociatedIdentity()).isNotNull();
    }
}
