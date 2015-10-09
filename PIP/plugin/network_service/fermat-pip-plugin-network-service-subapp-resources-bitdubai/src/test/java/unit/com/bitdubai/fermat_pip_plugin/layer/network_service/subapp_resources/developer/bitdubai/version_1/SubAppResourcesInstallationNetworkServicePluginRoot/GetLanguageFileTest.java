package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.github.GithubConnection;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.database.SubAppResourcesInstallationNetworkServiceDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.database.SubAppResourcesNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.estructure.Repository;
import com.googlecode.catchexception.CatchException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 30/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetLanguageFileTest {

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
    private SubAppResourcesInstallationNetworkServiceDAO subAppResourcesDAO = mock(SubAppResourcesInstallationNetworkServiceDAO.class);

    @Mock
    private GithubConnection githubConnection;

    @Mock
    private XMLParser mockXMLParser;
    @Mock
    private Repository repository = mock(Repository.class);
    String repoManifest = "<skin ></skin >";

    private String p;

    @Mock
    FermatEvent mockFermatEvent;

    private UUID skinId;
    private UUID testOwnerId;
    private String walletPublicKey;

    SubAppResourcesInstallationNetworkServicePluginRoot subAppResourcesInstallationNetworkServicePluginRoot;
    @Mock
    private DatabaseTableFactory mockFactoryTable;

    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTableRecord mockDatabaseTableRecord;

    @Mock
    private DatabaseTable mockDatabaseTable;

    @Mock
    private DatabaseTransaction mockTransaction;


    private String path;

    private String skinName;

    private String navigationStructureVersion;
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
        when(mockEventManager.getNewEvent(EventType.WALLET_UNINSTALLED)).thenReturn(mockFermatEvent);
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());

        setUpVariable1();

    }
    public void setUpVariable1() throws Exception {
        testOwnerId = UUID.randomUUID();
        skinId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockFactoryTable);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);

        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);

        when(mockDatabase.newTransaction()).thenReturn(mockTransaction);

        //when(new SubAppResourcesInstallationNetworkServiceDAO(mockPluginDatabaseSystem)).thenReturn(subAppResourcesDAO);
        subAppResourcesDAO = new SubAppResourcesInstallationNetworkServiceDAO(mockPluginDatabaseSystem);

        path = "path1";
        skinName = "skinName1";
        navigationStructureVersion = "version1";

        repository = new Repository(skinName, navigationStructureVersion, path);
        //when(new Repository(anyString(),anyString(),anyString())).thenReturn(repository);
        //subAppResourcesDAO.initializeDatabase(testOwnerId, SubAppResourcesNetworkServiceDatabaseConstants.DATABASE_NAME);
        catchException(subAppResourcesDAO).initializeDatabase(testOwnerId, SubAppResourcesNetworkServiceDatabaseConstants.DATABASE_NAME);
        subAppResourcesInstallationNetworkServicePluginRoot.start();
        skinId = UUID.randomUUID();
        catchException(subAppResourcesDAO).createRepository(repository, skinId);
        //subAppResourcesDAO.createRepository(repository, skinId);
        catchException(subAppResourcesInstallationNetworkServicePluginRoot).
                installLanguageForSubApp(
                        anyString(),
                        anyString(),
                        anyString(),
                        any(UUID.class),
                        anyString(),
                        anyString());
    }
    @Test
    public void testGetLanguageFile_FileNotFound() throws Exception{

        //when(subAppResourcesDAO.getRepository(skinId)).thenReturn(repository);
        //subAppResourcesInstallationNetworkServicePluginRoot.getLanguageFile(testOwnerId, walletPublicKey, "");
        catchException(subAppResourcesInstallationNetworkServicePluginRoot).getLanguageFile(skinId,"walletPublicKey", "fileName");
       System.out.println(CatchException.<Exception>caughtException());

        //assertThat(CatchException.<Exception>caughtException()).isNull();
    }
}
