package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTable;

import android.app.Activity;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseAggregateFunctionType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
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

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by natalia on 14/09/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class GetterTest {
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
        testTableFactory.addColumn("testColumn3", DatabaseDataType.STRING, 10, false);
        testDatabase.createTable(testTableFactory);
    }

    @Before
    public void setUp() throws Exception {
        setUpDatabase();
        setUpTable();
    }


    @Test
    public void getTableNameTest() throws Exception {
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        assertThat(testDatabaseTable.getTableName()).isInstanceOf(String.class);

    }

    @Test
    public void getEmptyTableFilterTest() throws Exception {
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        assertThat(testDatabaseTable.getEmptyTableFilter()).isInstanceOf(DatabaseTableFilter.class);

    }


    @Test
    public void getEmptyTableFilterGroupTest() throws Exception {
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        fail("not sure why");
    }

    @Test
    public void getEmptyRecordTest() throws Exception {
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        assertThat(testDatabaseTable.getEmptyRecord()).isInstanceOf(DatabaseTableRecord.class);

    }

    @Test
    public void getFilterGroupTest() throws Exception {
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        List<DatabaseTableFilter> databaseTableFilterList = new ArrayList<>();
        List<DatabaseTableFilterGroup> databaseTableFilterGroupList = new ArrayList<>();
        testDatabaseTable.setFilterGroup(databaseTableFilterList, databaseTableFilterGroupList, DatabaseFilterOperator.OR);
    }

    @Test
    public void getFiltersTest() throws Exception {
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        testDatabaseTable.addStringFilter("testColumn2", "ss", DatabaseFilterType.EQUAL);
        testDatabaseTable.addUUIDFilter("testColumn3", UUID.randomUUID(), DatabaseFilterType.EQUAL);
    }

    @Test
    public void getTableSelectOperatorTest() throws Exception {
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        testDatabaseTable.addAggregateFunction("testColumn1", DataBaseAggregateFunctionType.SUM, "total");

        assertThat(testDatabaseTable.getTableAggregateFunction()).isInstanceOf(List.class);

    }

    @Test
    public void getRecordFromPkTest_RecordsListLoaded() throws Exception {

        DatabaseTableFactory testTable1 = new AndroidDatabaseTableFactory("table2");
        testTable1.addColumn("testColumn1", DatabaseDataType.INTEGER, 0, false);
        testTable1.addColumn("pk", DatabaseDataType.INTEGER, 0, false);
        testTable1.addColumn("testColumn2", DatabaseDataType.STRING, 10, false);
        testDatabase.createTable(testTable1);

        testDatabaseTable = testDatabase.getTable("table2");

        DatabaseTableRecord testTableRecord = testDatabaseTable.getEmptyRecord();
        testTableRecord.setIntegerValue("testColumn1", 1);
        testTableRecord.setIntegerValue("pk", 1);
        testTableRecord.setStringValue("testColumn2", "prueba");

        testDatabaseTable.insertRecord(testTableRecord);

        assertThat(testDatabaseTable.getRecordFromPk("1")).isInstanceOf(DatabaseTableRecord.class);
    }

    @Test
    public void toStringTest() throws Exception {
        testDatabaseTable = testDatabase.getTable("otherTable");
        String table = testDatabaseTable.toString();
        assertThat(table).isNotNull();
    }


    @Test
    public void setStateFilterTest() throws Exception {
        testDatabaseTable = testDatabase.getTable("otherTable");
        //testDatabaseTable.setStateFilter("testColum2", WalletFactoryProjectState.CLOSED, DatabaseFilterType.EQUAL);
        fail("not sure why");
    }


    @Test
    public void clearAllFiltersTest() throws Exception {
        testDatabaseTable = testDatabase.getTable("otherTable");

        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        testDatabaseTable.addStringFilter("testColumn2", "ss", DatabaseFilterType.EQUAL);
        testDatabaseTable.addUUIDFilter("testColumn3", UUID.randomUUID(), DatabaseFilterType.EQUAL);

        testDatabaseTable.clearAllFilters();
    }


}
