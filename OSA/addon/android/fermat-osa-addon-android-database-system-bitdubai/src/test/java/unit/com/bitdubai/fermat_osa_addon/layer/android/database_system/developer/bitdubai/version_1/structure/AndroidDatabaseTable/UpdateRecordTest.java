package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTable;

import android.app.Activity;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
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

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 15/07/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class UpdateRecordTest {

    private Activity mockActivity;
    private String mockContext;

    private AndroidDatabase testDatabase;
    private DatabaseTable testDatabaseTable;
    private UUID testOwnerId;
    private String testDatabaseName = "testDatabase";

    private String testTableName = "testTable";

    private DatabaseTableFactory testTableFactory;

    private DatabaseTableRecord testTableRecord;

    public void setUpDatabase() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = "test"; //shadowOf(mockActivity).getApplicationContext();
        testOwnerId = UUID.randomUUID();
        testDatabase = new AndroidDatabase(mockContext, testOwnerId, testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
    }

    public void setUpTable() throws Exception {
        testTableFactory = new AndroidDatabaseTableFactory(testTableName);
        testTableFactory.addColumn("testColumn1", DatabaseDataType.INTEGER, 0, true);
        testTableFactory.addColumn("testColumn2", DatabaseDataType.STRING, 10, false);
        testTableFactory.addIndex("testColumn1");
        testDatabase.createTable(testTableFactory);
        testDatabaseTable = testDatabase.getTable(testTableName);

    }

    public void setUpRecord() throws Exception {
        DatabaseTableRecord testRecord = testDatabaseTable.getEmptyRecord();
        testRecord.setIntegerValue("testColumn1", 1);
        testRecord.setStringValue("testColumn2", "test");
        testDatabaseTable.insertRecord(testRecord);
    }

    @Before
    public void setUp() throws Exception {
        setUpDatabase();
        setUpTable();
        setUpRecord();
    }


    @Test
    public void InsertRecord_Succefuly_TrowsCantUpdateRecordException() throws Exception {
        testDatabaseTable.loadToMemory();
        testTableRecord = testDatabaseTable.getRecords().get(0);
        testTableRecord.setStringValue("testColumn2", "prueba2");

        testDatabaseTable.updateRecord(testTableRecord);

        DatabaseTableRecord testRecord = testDatabaseTable.getRecords().get(0);

        assertThat(testRecord.getStringValue("testColumn2")).isEqualTo(testTableRecord.getStringValue("testColumn2"));

    }

}


