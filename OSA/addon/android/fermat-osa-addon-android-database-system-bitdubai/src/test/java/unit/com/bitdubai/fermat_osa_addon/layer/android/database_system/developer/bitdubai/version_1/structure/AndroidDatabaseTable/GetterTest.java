package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTable;

import android.app.Activity;
import android.content.Context;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseSelectOperatorType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableColumn;
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

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by natalia on 14/09/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class GetterTest {
    private Activity mockActivity;
    private Context mockContext;

    private AndroidDatabase testDatabase;
    private DatabaseTable testDatabaseTable;
    private UUID testOwnerId;
    private String testDatabaseName = "testDatabase";

    private String testTableName = "testTable";

    private DatabaseTableFactory testTableFactory;

    public void setUpDatabase() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();
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
    public void getTableNameTest() throws Exception{
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        assertThat(testDatabaseTable.getTableName()).isInstanceOf(String.class);

    }

    @Test
    public void getEmptyTableFilterTest() throws Exception{
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        assertThat(testDatabaseTable.getEmptyTableFilter()).isInstanceOf(DatabaseTableFilter.class);

    }


    @Test
    public void getEmptyTableFilterGroupTest() throws Exception{
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        //ssertThat(testDatabaseTable.getEmptyTableFilterGroup()).isInstanceOf(DatabaseTableFilterGroup.class);
        fail("not sure why");
    }

    @Test
    public void getEmptyRecordTest() throws Exception{
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        assertThat(testDatabaseTable.getEmptyRecord()).isInstanceOf(DatabaseTableRecord.class);

    }

    @Test
    public void getFilterGroupTest() throws Exception{
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        List<DatabaseTableFilter> databaseTableFilterList = new ArrayList<>();
        List<DatabaseTableFilterGroup>  databaseTableFilterGroupList = new ArrayList<>();
        testDatabaseTable.setFilterGroup(databaseTableFilterList, databaseTableFilterGroupList, DatabaseFilterOperator.OR);

        assertThat(testDatabaseTable.getFilterGroup()).isInstanceOf(DatabaseTableFilterGroup.class);

    }


    @Test
    public void getFiltersTest() throws Exception{
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
       testDatabaseTable.setStringFilter("testColumn2", "ss", DatabaseFilterType.EQUAL);
        testDatabaseTable.setUUIDFilter("testColumn3", UUID.randomUUID(), DatabaseFilterType.EQUAL);

        assertThat(testDatabaseTable.getFilters()).isInstanceOf(List.class);



    }

    @Test
    public void getTableSelectOperatorTest() throws Exception{
        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        testDatabaseTable.setSelectOperator("testColumn1", DataBaseSelectOperatorType.SUM, "total");

        assertThat(testDatabaseTable.getTableSelectOperator()).isInstanceOf(List.class);

    }

    @Test
    public void getRecordFromPkTest_RecordsListLoaded() throws Exception{

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
    public void newColumTest() throws Exception{
        testDatabaseTable = testDatabase.getTable("otherTable");
        DatabaseTableColumn column = testDatabaseTable.newColumn();

    }


    @Test
    public void toStringTest() throws Exception{
        testDatabaseTable = testDatabase.getTable("otherTable");
        String table = testDatabaseTable.toString();
        assertThat(table).isNotNull();
    }


    @Test
    public void setStateFilterTest() throws Exception{
        testDatabaseTable = testDatabase.getTable("otherTable");
        //testDatabaseTable.setStateFilter("testColum2", WalletFactoryProjectState.CLOSED, DatabaseFilterType.EQUAL);
        fail("not sure why");
    }


    @Test
    public void clearAllFiltersTest() throws Exception{
        testDatabaseTable = testDatabase.getTable("otherTable");

        testDatabaseTable = testDatabase.getTable(testDatabaseName);
        testDatabaseTable.setStringFilter("testColumn2", "ss", DatabaseFilterType.EQUAL);
        testDatabaseTable.setUUIDFilter("testColumn3", UUID.randomUUID(), DatabaseFilterType.EQUAL);

        testDatabaseTable.clearAllFilters();

        List<DatabaseTableFilter> filterList = testDatabaseTable.getFilters();

        assertThat(filterList).isNull();
    }


}
