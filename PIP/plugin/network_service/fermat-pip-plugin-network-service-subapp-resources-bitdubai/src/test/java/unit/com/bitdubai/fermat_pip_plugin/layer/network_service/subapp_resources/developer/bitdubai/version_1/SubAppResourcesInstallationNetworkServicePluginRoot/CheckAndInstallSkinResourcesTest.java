package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.github.GithubConnection;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.estructure.Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 30/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CheckAndInstallSkinResourcesTest {
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

    @Mock
    private Repository repository;

    @Mock
    private GithubConnection githubConnection;

    @Mock
    private XMLParser mockXMLParser;

    String repoManifest = "<skin ></skin >";
    String walletPublicKey;

    SubAppResourcesInstallationNetworkServicePluginRoot subAppResourcesInstallationNetworkServicePluginRoot;

    @Before
    public void setUp() throws Exception {


        subAppResourcesInstallationNetworkServicePluginRoot = new SubAppResourcesInstallationNetworkServicePluginRoot();
        subAppResourcesInstallationNetworkServicePluginRoot.setPluginFileSystem(pluginFileSystem);
        subAppResourcesInstallationNetworkServicePluginRoot.setEventManager(mockEventManager);
        subAppResourcesInstallationNetworkServicePluginRoot.setErrorManager(errorManager);

        subAppResourcesInstallationNetworkServicePluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(githubConnection.getFile(anyString())).thenReturn(repoManifest);

        when(mockEventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION)).thenReturn(mockFermatEventListener);
        when(pluginFileSystem.getTextFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(mockPluginTextFile);
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());
        subAppResourcesInstallationNetworkServicePluginRoot.start();

    }
    @Test
    public void testCheckAndInstallSkinResources_ThrowsCantCheckResourcesException() throws Exception{
       // Skin skin;
     //   String path ="seed-resources/wallet_resources/bitDubai/reference_wallet/bitcoin_wallet/navigation_structure/1.0.0/navigation_structure.xml";
//TODO BORRAR ESTE TEST !!!!

    }


}
