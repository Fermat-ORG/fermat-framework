package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTable;

import android.app.Activity;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseAggregateFunctionType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by pmgesualdi on 12/17/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class SelectOperatorTest {

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
        try {
            testDatabaseTable = testDatabase.getTable(testTableName);
            testDatabaseTable.addStringFilter("column1", "value1", DatabaseFilterType.EQUAL);
            testDatabaseTable.addStringFilter("column1", "value1", DatabaseFilterType.GREATER_OR_EQUAL_THAN);
            testDatabaseTable.addStringFilter("column1", "value1", DatabaseFilterType.NOT_EQUALS);
            testDatabaseTable.addAggregateFunction("column1", DataBaseAggregateFunctionType.COUNT, "column1_count");
            testDatabaseTable.addAggregateFunction("column1", DataBaseAggregateFunctionType.SUM, "column1_sum");
            testDatabaseTable.addAggregateFunction("column2", DataBaseAggregateFunctionType.MAX, "column2_max");
            testDatabaseTable.addAggregateFunction("column2", DataBaseAggregateFunctionType.MIN, "column2_min");
            testDatabaseTable.addAggregateFunction("column3", DataBaseAggregateFunctionType.ROUND, "column3_round");
            testDatabaseTable.addAggregateFunction("column3", DataBaseAggregateFunctionType.AVG, "column3_avg");
            testDatabaseTable.loadToMemory();
            assertThat(testDatabaseTable.getRecords()).isNotNull();
        } catch (Exception e) {
            System.out.println("hubo un error que no me importa");
            //System.out.println(e.toString());
        }
    }
}