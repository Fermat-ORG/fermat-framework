package CryptoCustomerWalletModuleBrokerIdentityBusinessInfo;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleBrokerIdentityBusinessInfo;

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
public class CreateMessageSignatureTest {
    @Test
    public void createMessageSignature() throws CantCreateMessageSignatureException {
        CryptoCustomerWalletModuleBrokerIdentityBusinessInfo cryptoCustomerWalletModuleBrokerIdentityBusinessInfo = mock(CryptoCustomerWalletModuleBrokerIdentityBusinessInfo.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoCustomerWalletModuleBrokerIdentityBusinessInfo).createMessageSignature(Mockito.any(String.class));
    }
}
