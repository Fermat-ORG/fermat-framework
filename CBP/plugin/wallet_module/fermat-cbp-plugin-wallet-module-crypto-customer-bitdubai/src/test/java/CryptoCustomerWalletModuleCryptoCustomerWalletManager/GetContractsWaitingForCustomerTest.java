package CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetContractsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by roy on 7/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetContractsWaitingForCustomerTest {
    @Test
    public void getContractsWaitingForCustomer() throws CantGetContractsWaitingForCustomerException {
        CryptoCustomerWalletModuleCryptoCustomerWalletManager cryptoCustomerWalletModuleCryptoCustomerWalletManager = mock(CryptoCustomerWalletModuleCryptoCustomerWalletManager.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(cryptoCustomerWalletModuleCryptoCustomerWalletManager).getContractsWaitingForCustomer(Mockito.any(Integer.class), Mockito.any(Integer.class));
    }
}
