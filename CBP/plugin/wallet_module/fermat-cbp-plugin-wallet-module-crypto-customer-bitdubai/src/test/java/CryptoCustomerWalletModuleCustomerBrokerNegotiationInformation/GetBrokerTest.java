package CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation;

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
public class GetBrokerTest {
    @Test
    public void getBroker() {
        CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation = mock(CryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.class);
        when(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.getBroker()).thenReturn(new ActorIdentity() {
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
        assertThat(cryptoCustomerWalletModuleCustomerBrokerNegotiationInformation.getBroker()).isNotNull();
    }
}
