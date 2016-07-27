package unit.com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

//import com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentFactory;

/**
 * Created by nelson on 17/09/15.
 */
public class GetFragmentTest {
    private final String ENUM_TYPE_CODE = "MF";

    //private CryptoCustomerWalletFragmentFactory fragmentFactory;

    @Mock
    private WalletSession session;

    @Mock
    private WalletResourcesProviderManager providerManager;


    @Before
    public void setUp() throws Exception {
        //fragmentFactory = new CryptoCustomerWalletFragmentFactory();
    }

    @Test
    public void fragmentFound() throws Exception {
        //Fragment actualFragment = fragmentFactory.getFragment(ENUM_TYPE_CODE, session, providerManager);

        //assertThat(actualFragment).isInstanceOf(fermatFragment.class);
    }

    @Test
    public void fragmentNotFound() throws Exception {
        //catchException(fragmentFactory).getFragment(null, session, providerManager);

        assertThat(caughtException()).isInstanceOf(FragmentNotFoundException.class);
    }
}