package unit.com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 17/09/15.
 */
public class GetFragmentTest {

    private CryptoBrokerWalletFragmentFactory fragmentFactory;

    @Mock
    private WalletSession session;

    @Mock
    private WalletResourcesProviderManager providerManager;

    @Mock
    private WalletSettings settings;


    @Before
    public void setUp() throws Exception {
        fragmentFactory = new CryptoBrokerWalletFragmentFactory();
    }

    @Test
    public void fragmentFound() throws Exception {
//        Fragment actualFragment = fragmentFactory.getFragment("CBPCBWHART", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(fermatFragment.class);
    }

    @Test
    public void fragmentsObjectsMatchTheirCodes() throws Exception {
//        Fragment actualFragment = fragmentFactory.getFragment("CBPCBWHART", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(MarketRateStatisticsFragment.class);
//
//        actualFragment = fragmentFactory.getFragment("CBPCBWHAODT", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(OpenNegotiationsTabFragment.class);
//
//        actualFragment = fragmentFactory.getFragment("CBPCBWHAOCT", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(OpenContractsTabFragment.class);
//
//        actualFragment = fragmentFactory.getFragment("CBPCBWHASS", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(StockStatisticsFragment.class);
//
//        actualFragment = fragmentFactory.getFragment("CBPCBWDD", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(OpenNegotiationDetailsFragment.class);
//
//        actualFragment = fragmentFactory.getFragment("CBPCBWCD", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(CloseNegotiationDetailsFragment.class);
//
//        actualFragment = fragmentFactory.getFragment("CBPCBWD", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(OpenContractDetailsFragment.class);
//
//        actualFragment = fragmentFactory.getFragment("CBPCBWC", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(CloseContractDetailsFragment.class);
//
//        actualFragment = fragmentFactory.getFragment("CBPCBWSP", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(ContractsHistoryActivityFragment.class);
//
//        actualFragment = fragmentFactory.getFragment("CBPCBWS", session, settings, providerManager);
//        assertThat(actualFragment).isInstanceOf(SettingsActivityFragment.class);
    }

    @Test
    public void fragmentNotFound() throws Exception {
//        catchException(fragmentFactory).getFragment(null, session, settings, providerManager);

        assertThat(caughtException()).isInstanceOf(FragmentNotFoundException.class);
    }
}