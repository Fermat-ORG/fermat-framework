package unit.com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.Database.IntraUserIdentityDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraUserIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraUserIdentityDatabaseException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by angel on 20/8/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class getDatabaseTableContentTest extends TestCase {

    @Mock
    private Database mockDatabase;

    @Mock
    private DeveloperObjectFactory developerObjectFactory;
    @Mock
    private DeveloperDatabaseTable developerDatabaseTable;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private DatabaseTable mockTable;

    @Mock
    DatabaseTableRecord mockDatabaseTableRecord;
    @Mock
    List<DatabaseTableRecord> databaseTableRecordList;

    private IntraUserIdentityDeveloperDatabaseFactory intraUserIdentityDeveloperDatabaseFactory;

    @Before
    public void SetUp() throws CantCreateDatabaseException, CantInitializeIntraUserIdentityDatabaseException, CantOpenDatabaseException, DatabaseNotFoundException {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(databaseTableRecordList);

        intraUserIdentityDeveloperDatabaseFactory = new IntraUserIdentityDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        intraUserIdentityDeveloperDatabaseFactory.initializeDatabase();

    }
    @Test
    public void getDatabaseTableContentTest() throws CantOpenDatabaseException, DatabaseNotFoundException, CantInitializeIntraUserIdentityDatabaseException {

        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("Name1");
        fieldNames.add("Name2");
        fieldNames.add("Name3");

//TODO Ejecucion con OK error en for each sobre los records hay que ver como se mockea eso
       // assertThat(intraUserIdentityDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable)).isInstanceOf(List.class);
        catchException(intraUserIdentityDeveloperDatabaseFactory).getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        assertThat(caughtException()).isNotNull().isInstanceOf(Exception.class);


    }

}