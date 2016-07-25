package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTable;

import android.app.Activity;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

;

/**
 * Created by natalia on 15/07/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class LoadToMemoryTest {

    private Activity mockActivity;
    private String mockContext;

    private AndroidDatabase testDatabase;
    private DatabaseTable testDatabaseTable;
    private UUID testOwnerId;
    private String testDatabaseName = "testDatabase";

    private String testTableName = "testTable";

    private DatabaseTableFactory testTableFactory;

    public void setUpDatabase() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = "test1"; //shadowOf(mockActivity).getApplicationContext();
        testOwnerId = UUID.randomUUID();
        testDatabase = new AndroidDatabase(mockContext, testOwnerId, testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
    }

    public void setUpTable() throws Exception {
        testTableFactory = new AndroidDatabaseTableFactory(testTableName);
        testTableFactory.addColumn("testColumn1", DatabaseDataType.INTEGER, 0, false);
        testTableFactory.addColumn("testColumn2", DatabaseDataType.STRING, 10, false);
        testDatabase.createTable(testTableFactory);
    }

    @Before
    public void setUp() throws Exception {
        setUpDatabase();
        setUpTable();
    }


    @Test
    public void loadTable_TableAlreadyExists_RecordsListLoaded() throws Exception {
        testDatabaseTable = testDatabase.getTable(testTableName);
        testDatabaseTable.loadToMemory();
        assertThat(testDatabaseTable.getRecords()).isNotNull();
    }

    @Test
    public void loadTable_TableFilters_RecordsListLoaded() throws Exception {
        testDatabaseTable = testDatabase.getTable(testTableName);
        testDatabaseTable.addStringFilter("testColumn1", "1", DatabaseFilterType.EQUAL);
        testDatabaseTable.loadToMemory();
        assertThat(testDatabaseTable.getRecords()).isNotNull();
    }

    @Test
    public void loadTable_TableOrder_RecordsListLoaded() throws Exception {
        testDatabaseTable = testDatabase.getTable(testTableName);
        testDatabaseTable.addFilterOrder("testColumn1", DatabaseFilterOrder.DESCENDING);
        testDatabaseTable.loadToMemory();
        assertThat(testDatabaseTable.getRecords()).isNotNull();
    }

    @Test
    public void loadTable_TableTop_RecordsListLoaded() throws Exception {
        testDatabaseTable = testDatabase.getTable(testTableName);
        testDatabaseTable.setFilterTop("10");
        testDatabaseTable.loadToMemory();
        assertThat(testDatabaseTable.getRecords()).isNotNull();
    }

    @Test
    public void loadTable_TableGroupFilter_RecordsListLoaded() throws Exception {
        testDatabaseTable = testDatabase.getTable(testTableName);
        List<DatabaseTableFilter> databaseTableFilterList = new ArrayList<>();
        List<DatabaseTableFilterGroup> databaseTableFilterGroupList = new ArrayList<>();
        testDatabaseTable.setFilterGroup(databaseTableFilterList, databaseTableFilterGroupList, DatabaseFilterOperator.OR);
        testDatabaseTable.loadToMemory();
        assertThat(testDatabaseTable.getRecords()).isNotNull();
    }

    @Test
    public void loadTable_TableDoesNotExist_ThrowsCantLoadTableToMemoryException() throws Exception {
        testDatabaseTable = testDatabase.getTable("otherTable");
        catchException(testDatabaseTable).loadToMemory();
        assertThat(caughtException()).isInstanceOf(CantLoadTableToMemoryException.class);
    }


}
