package unit.com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;
//import com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

/**
 * Created by nelson on 17/09/15.
 */
public class GetFragmentTest {
    private final String ENUM_TYPE_CODE = "MF";

    //private CryptoBrokerWalletFragmentFactory fragmentFactory;

    @Mock
    private WalletSession session;

    @Mock
    private WalletResourcesProviderManager providerManager;


    @Before
    public void setUp() throws Exception {
      //  fragmentFactory = new CryptoBrokerWalletFragmentFactory();
    }

    @Test
    public void fragmentFound() throws Exception {
        //Fragment actualFragment = fragmentFactory.getFragment(ENUM_TYPE_CODE, session, providerManager);
        fail("fail");
        //assertThat(actualFragment).isInstanceOf(FermatFragment.class);
    }

    @Test
    public void fragmentNotFound() throws Exception {
        //catchException(fragmentFactory).getFragment(null, session, providerManager);

        assertThat(caughtException()).isInstanceOf(FragmentNotFoundException.class);
    }
}