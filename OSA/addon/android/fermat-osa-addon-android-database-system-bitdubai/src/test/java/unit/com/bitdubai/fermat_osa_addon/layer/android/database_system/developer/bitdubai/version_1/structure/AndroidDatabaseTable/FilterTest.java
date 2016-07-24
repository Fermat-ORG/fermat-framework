package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTable;

import android.app.Activity;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
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

/**
 * Created by angel on 7/8/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class FilterTest {
    private Activity mockActivity;
    private String mockContext;

    private AndroidDatabase testDatabase;
    private DatabaseTable testDatabaseTable;
    private UUID testOwnerId;
    private String testDatabaseName = "testDatabase";

    private String testTableName = "testTable";

    private DatabaseTableFactory testTableFactory;

    private DatabaseTableRecord testTableRecord_1, testTableRecord_2, testTableRecord_3;

    public void setUpDatabase() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = "test1"; //shadowOf(mockActivity).getApplicationContext();
        testOwnerId = UUID.randomUUID();
        testDatabase = new AndroidDatabase(mockContext, testOwnerId, testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
    }

    public void setUpTable() throws Exception {
        testTableFactory = new AndroidDatabaseTableFactory(testTableName);
        testTableFactory.addColumn("testColumn1", DatabaseDataType.INTEGER, 0, true);
        testTableFactory.addColumn("testColumn2", DatabaseDataType.STRING, 10, false);
        testTableFactory.addColumn("testColumn3", DatabaseDataType.STRING, 10, false);
        testTableFactory.addColumn("pk", DatabaseDataType.STRING, 10, false);
        testTableFactory.addIndex("testColumn1");
        testDatabase.createTable(testTableFactory);
        testDatabaseTable = testDatabase.getTable(testTableName);

        testTableRecord_1 = testDatabaseTable.getEmptyRecord();
        testTableRecord_1.setIntegerValue("testColumn1", 1);
        testTableRecord_1.setStringValue("testColumn2", "valor 1");
        testTableRecord_1.setStringValue("testColumn3", "valor 2");
        testTableRecord_1.setStringValue("pk", "valor pk");

        testDatabaseTable.insertRecord(testTableRecord_1);
    }

    @Before
    public void setUp() throws Exception {
        setUpDatabase();
        setUpTable();
    }

    @Test
    public void Filters() throws Exception {

        testDatabaseTable.loadToMemory();

        testTableRecord_2 = testDatabaseTable.getEmptyRecord();
        testTableRecord_2.setIntegerValue("testColumn1", 1);
        testTableRecord_2.setStringValue("testColumn2", "valor 1");


        testDatabaseTable.setFilterOffSet("10");
        testDatabaseTable.setFilterTop("100");
        testDatabaseTable.addFilterOrder("testColumn1", DatabaseFilterOrder.DESCENDING);
        testDatabaseTable.addFilterOrder("testColumn2", DatabaseFilterOrder.ASCENDING);

        testDatabaseTable.addStringFilter("testColumn2", "valor 1", DatabaseFilterType.LIKE);
        testDatabaseTable.addStringFilter("testColumn2", "valor 1", DatabaseFilterType.EQUAL);
        testDatabaseTable.addStringFilter("testColumn1", "0", DatabaseFilterType.GREATER_THAN);
        testDatabaseTable.addStringFilter("testColumn1", "2", DatabaseFilterType.LESS_THAN);

    }
}
