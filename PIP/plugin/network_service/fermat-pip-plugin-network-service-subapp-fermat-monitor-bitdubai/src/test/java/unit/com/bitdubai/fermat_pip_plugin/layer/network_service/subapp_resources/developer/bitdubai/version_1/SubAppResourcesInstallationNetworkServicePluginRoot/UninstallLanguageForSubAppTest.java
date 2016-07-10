package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.github.GitHubConnection;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesNetworkServicePluginRoot;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.database.SubAppResourcesInstallationNetworkServiceDAO;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.database.SubAppResourcesNetworkServiceDatabaseConstants;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.estructure.Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 30/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class UninstallLanguageForSubAppTest {

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
    private GitHubConnection gitHubConnection;

    @Mock
    private XMLParser mockXMLParser;

    @Mock
    private PluginTextFile layoutFile;
    @Mock
    private PluginBinaryFile imageFile;

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
    @Mock
    private Skin skin;
    @Mock
    private Object object;
    @Mock
    private XMLParser xmlParser = mock(XMLParser.class);
    @Mock
    private List<DatabaseTableRecord> databaseTableRecordList;
    UUID skinId;

    String repoManifest = "<skin ></skin >";

    SubAppResourcesNetworkServicePluginRoot subAppResourcesNetworkServicePluginRoot;
    private SubAppResourcesInstallationNetworkServiceDAO subAppResourcesDAO;

    @Before
    public void setUp() throws Exception {
        subAppResourcesNetworkServicePluginRoot = new SubAppResourcesNetworkServicePluginRoot();
        subAppResourcesNetworkServicePluginRoot.setPluginFileSystem(pluginFileSystem);
        subAppResourcesNetworkServicePluginRoot.setEventManager(mockEventManager);
        subAppResourcesNetworkServicePluginRoot.setErrorManager(errorManager);
        subAppResourcesNetworkServicePluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(gitHubConnection.getFile(anyString())).thenReturn(repoManifest);
        when(pluginFileSystem.createTextFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(layoutFile);
        when(pluginFileSystem.createBinaryFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(imageFile);

        when(mockEventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION)).thenReturn(mockFermatEventListener);
        when(pluginFileSystem.getTextFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(mockPluginTextFile);
        setUpDataBase();
    }

    public void setUpDataBase() throws Exception {

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockFactoryTable);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.newTransaction()).thenReturn(mockTransaction);
        subAppResourcesDAO = new SubAppResourcesInstallationNetworkServiceDAO(mockPluginDatabaseSystem);
        subAppResourcesDAO = new SubAppResourcesInstallationNetworkServiceDAO(mockPluginDatabaseSystem);

        String path = "path1";
        String skinName = "skinName1";
        String navigationStructureVersion = "version1";
        repository = new Repository(skinName, navigationStructureVersion, path);

        subAppResourcesDAO.initializeDatabase(UUID.randomUUID(), SubAppResourcesNetworkServiceDatabaseConstants.DATABASE_NAME);
        skinId = UUID.randomUUID();
        subAppResourcesDAO.createRepository(repository, skinId);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        mockDatabaseTable.addUUIDFilter(SubAppResourcesNetworkServiceDatabaseConstants.REPOSITORIES_SKIN_ID_COLUMN_NAME, skinId, DatabaseFilterType.EQUAL);
        mockDatabaseTable.loadToMemory();
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(databaseTableRecordList.get(anyInt())).thenReturn(mockDatabaseTableRecord);
        when(mockDatabaseTableRecord.getStringValue(anyString())).thenReturn(anyString());
    }

    @Test
    public void testUninstallLanguageForSubApp_FileFound() throws Exception {
        subAppResourcesNetworkServicePluginRoot.start();
        catchException(subAppResourcesNetworkServicePluginRoot).uninstallLanguageForSubApp(UUID.randomUUID(), "en", "subAppPublicKey");
        assertThat(caughtException()).isNull();
    }

    @Test
    public void testUninstallLanguageForSubApp_FileNotFoundException() throws Exception {
        subAppResourcesNetworkServicePluginRoot.start();
        catchException(subAppResourcesNetworkServicePluginRoot).uninstallLanguageForSubApp(null, "en", "subAppPublicKey");
        assertThat(caughtException()).isNotNull();
    }
}
