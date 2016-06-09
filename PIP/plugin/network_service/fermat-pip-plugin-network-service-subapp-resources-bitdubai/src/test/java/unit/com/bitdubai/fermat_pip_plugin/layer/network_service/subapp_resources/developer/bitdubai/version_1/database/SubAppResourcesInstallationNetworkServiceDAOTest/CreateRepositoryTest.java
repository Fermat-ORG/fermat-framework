package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.database.SubAppResourcesInstallationNetworkServiceDAOTest;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
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
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 30/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateRepositoryTest {
    private SubAppResourcesInstallationNetworkServiceDAO subAppResourcesInstallationNetworkServiceDAO;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

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


    private UUID testOwnerId;

    private UUID skinId;

    private Repository repository;

    private String path;

    private String skinName;

    private String navigationStructureVersion;

    @Before
    public void setUp() throws Exception {
        testOwnerId = UUID.randomUUID();
        skinId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockFactoryTable);
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);

        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);

        when(mockDatabase.newTransaction()).thenReturn(mockTransaction);

        subAppResourcesInstallationNetworkServiceDAO = new SubAppResourcesInstallationNetworkServiceDAO(mockPluginDatabaseSystem);

        path = "path1";
        skinName = "skinName1";
        navigationStructureVersion = "version1";
        repository = new Repository(skinName, navigationStructureVersion, path);

        subAppResourcesInstallationNetworkServiceDAO.initializeDatabase(testOwnerId, SubAppResourcesNetworkServiceDatabaseConstants.DATABASE_NAME);
    }

    @Test
    public void createRepositoryTest_CreateOk_ThrowsCantCreateRepositoryException() throws Exception {

        catchException(subAppResourcesInstallationNetworkServiceDAO).createRepository(repository, skinId);
        //repository = subAppResourcesInstallationNetworkServiceDAO.getRepository(skinId);
        System.out.println(repository);
        assertThat(CatchException.<Exception>caughtException()).isNull();

    }

    @Test
    public void createRepositoryTest_ErrorRepositoryNull_ThrowsCantCreateRepositoryException() throws Exception {

        Repository repository1 = new Repository(null, null, null);

        catchException(subAppResourcesInstallationNetworkServiceDAO).createRepository(repository1, skinId);


        assertThat(CatchException.<Exception>caughtException()).isNotNull();

    }

    @Test
    public void initializeDatabaseTest_ErrorTransaction_ThrowsCantInitializeNetworkServicesWalletResourcesDatabaseException() throws Exception {

        when(mockDatabase.newTransaction()).thenReturn(null);
        catchException(subAppResourcesInstallationNetworkServiceDAO).createRepository(null, null);
        //System.out.println(CatchException.<Exception>caughtException());
        assertThat(CatchException.<Exception>caughtException()).isNotNull();

    }

}
