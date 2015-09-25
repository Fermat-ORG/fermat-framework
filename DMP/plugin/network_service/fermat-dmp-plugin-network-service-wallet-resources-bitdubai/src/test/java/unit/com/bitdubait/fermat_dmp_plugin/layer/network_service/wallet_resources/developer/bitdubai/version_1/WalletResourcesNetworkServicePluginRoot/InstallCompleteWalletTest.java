package unit.com.bitdubait.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.github.GithubConnection;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.exceptions.WalletResourcesInstalationException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.structure.Repository;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import junit.framework.TestCase;

import org.junit.Before;

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
 * Created by natalia on 09/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class InstallCompleteWalletTest extends TestCase {

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
    private DatabaseTable mockTable;

    @Mock
    private DatabaseTableRecord mockTableRecord;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private PluginTextFile mockPluginTextFile;

    @Mock
    private PluginBinaryFile mockPluginBinaryFile;

    @Mock
    private Repository repository;

    @Mock
    private GithubConnection githubConnection;

    @Mock
    private XMLParser mockXMLParser;

    String repoManifest = "<skin ></skin >";

    WalletResourcesNetworkServicePluginRoot walletResourcePluginRoot;

    @Before
    public void setUp() throws Exception {


        walletResourcePluginRoot = new WalletResourcesNetworkServicePluginRoot();
        walletResourcePluginRoot.setPluginFileSystem(pluginFileSystem);
        walletResourcePluginRoot.setEventManager(mockEventManager);
        walletResourcePluginRoot.setErrorManager(errorManager);

        walletResourcePluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockTableRecord);
        when(githubConnection.getFile(anyString())).thenReturn(repoManifest);

        when(mockEventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION)).thenReturn(mockFermatEventListener);
        when(pluginFileSystem.getTextFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(mockPluginTextFile);
        when(pluginFileSystem.createBinaryFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(mockPluginBinaryFile);
        when(pluginFileSystem.createTextFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(mockPluginTextFile);

    }


    @Test
    public void testInstallCompleteWallet_ThrowsWalletResourcesInstallationException() throws Exception {

        walletResourcePluginRoot.start();

        catchException(walletResourcePluginRoot).installCompleteWallet(WalletCategory.REFERENCE_WALLET.getCode(), WalletType.REFERENCE.getCode(), "bitDubai", "medium", "default", "en", "1.0.0", "walletPublicKey");
                assertThat(caughtException()).isNull();

    }


    @Test
    public void testInstallCompleteWallet_FileNotFoundThrowsWalletResourcesInstalationException() throws Exception {

        walletResourcePluginRoot.start();
        catchException(walletResourcePluginRoot).installCompleteWallet("reference_wallet", "bitcoin_wallet", "bitDubai", "medium", "skin", "languageName", "navigationStructureVersion","walletPublicKey");
        assertThat(caughtException()).isInstanceOf(WalletResourcesInstalationException.class);

    }
}
