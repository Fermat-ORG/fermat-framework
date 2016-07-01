package unit.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 03/07/15.
 */

/**
 * The Resource Repository  is not created, then test return error for all case.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetLayoutResourceTest extends TestCase {

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
    private FermatEventListener mockFermatEventListener;

    @Mock
    private EventManager mockEventManager;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private PluginTextFile mockPluginTextFile;

    WalletResourcesNetworkServicePluginRoot walletResourcePluginRoot;

    @Before
    public void setUp() throws Exception {
        walletResourcePluginRoot = new WalletResourcesNetworkServicePluginRoot();
        walletResourcePluginRoot.setPluginFileSystem(pluginFileSystem);

        walletResourcePluginRoot.setErrorManager(errorManager);
        walletResourcePluginRoot.setEventManager(mockEventManager);
        walletResourcePluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        when(mockEventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION)).thenReturn(mockFermatEventListener);
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        when(pluginFileSystem.getTextFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(mockPluginTextFile);

        when(mockPluginTextFile.getContent()).thenReturn("layoutContent");

        walletResourcePluginRoot.start();
    }


    @Test
    public void testgetImageResource_ReturnOk_ThrowsCantGetResourcesException() throws Exception {


        //catchException(walletResourcePluginRoot).getLayoutResource("wallets_kids_fragment_balance.txt", ScreenOrientation.LANDSCAPE, UUID.randomUUID());
        //assertThat(caughtException()).isNull();


    }

    @Ignore
    @Test
    public void testcheckResources_TheResourcesRepositoryNotExist_ThrowsCantGetResourcesException() throws Exception {


        //catchException(walletResourcePluginRoot).getLayoutResource("wallets_kids_fragment_balance.txt");
        //assertThat(caughtException()).isInstanceOf(CantGetResourcesException.class);
        caughtException().printStackTrace();
    }

    @Ignore
    @Test
    public void testcheckResources_fileNotFound_ThrowsCantGetResourcesException() throws Exception {

        //catchException(walletResourcePluginRoot).getLayoutResource("layout1.xml");
        //assertThat(caughtException()).isInstanceOf(CantGetResourcesException.class);
        caughtException().printStackTrace();
    }
}


