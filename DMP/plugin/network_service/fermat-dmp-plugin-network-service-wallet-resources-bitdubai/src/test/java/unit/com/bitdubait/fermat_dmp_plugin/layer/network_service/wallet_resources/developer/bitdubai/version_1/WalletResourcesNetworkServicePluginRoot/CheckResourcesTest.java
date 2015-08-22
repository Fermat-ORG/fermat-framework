package unit.com.bitdubait.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 03/07/15.
 */

/**
 * The Resource Repository  is not created, then test return error for all case.
 */
@RunWith(MockitoJUnitRunner.class)
public class CheckResourcesTest extends TestCase {

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    ErrorManager errorManager;

    /**
     * UsesFileSystem Interface member variables.
     */
    @Mock
    PluginFileSystem pluginFileSystem;


    /**
     * DealWithEvents Iianterface member variables.
     */
    @Mock
    EventManager eventManager;



    WalletResourcesNetworkServicePluginRoot walletResourcePluginRoot;

    @Before
    public void setUp() throws Exception {
        walletResourcePluginRoot = new WalletResourcesNetworkServicePluginRoot();
        //walletResourcePluginRoot.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);
        walletResourcePluginRoot.setPluginFileSystem(pluginFileSystem);
        walletResourcePluginRoot.setEventManager(eventManager);
        walletResourcePluginRoot.setErrorManager(errorManager);
    }

    @Ignore
    @Test
    public void testcheckResources_TheResourcesHasAlreadyBeenDownload_ThrowsCantCheckResourcesException() throws Exception {


        //catchException(walletResourcePluginRoot).checkResources();
        //assertThat(caughtException()).isInstanceOf(CantCheckResourcesException.class);
        caughtException().printStackTrace();
    }

    @Ignore
    @Test
    public void testcheckResources_TheResourceRepositoryNotExist_ThrowsCantCheckResourcesException() throws Exception {

        //walletResourcePluginRoot.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI);

        //catchException(walletResourcePluginRoot).checkResources();
        //assertThat(caughtException()).isInstanceOf(CantCheckResourcesException.class);
        caughtException().printStackTrace();
    }

    @Ignore
    @Test
    public void testcheckResources_TheResourcesFieldsCannotPersist_ThrowsCantCheckResourcesException() throws Exception {

        walletResourcePluginRoot.setPluginFileSystem(null);
        //catchException(walletResourcePluginRoot).checkResources();
        //assertThat(caughtException()).isInstanceOf(CantCheckResourcesException.class);
        caughtException().printStackTrace();
    }
}
