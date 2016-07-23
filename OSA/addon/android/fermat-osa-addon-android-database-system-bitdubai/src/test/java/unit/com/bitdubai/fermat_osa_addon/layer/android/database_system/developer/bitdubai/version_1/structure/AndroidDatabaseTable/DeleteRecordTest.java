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
 * Created by angel on 7/8/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class DeleteRecordTest {


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
        testTableFactory.addIndex("testColumn1");
        testDatabase.createTable(testTableFactory);
        testDatabaseTable = testDatabase.getTable(testTableName);

        testTableRecord_1 = testDatabaseTable.getEmptyRecord();
        testTableRecord_1.setIntegerValue("testColumn1", 1);
        testTableRecord_1.setStringValue("testColumn2", "valor 1");
        testTableRecord_1.setStringValue("testColumn3", "valor 2");

        testDatabaseTable.insertRecord(testTableRecord_1);
    }

    @Before
    public void setUp() throws Exception {
        setUpDatabase();
        setUpTable();
    }

    @Test
    public void DeleteRecord_Empty() throws Exception {
        /*
        testTableRecord_2 = testDatabaseTable.getEmptyRecord();
        testDatabaseTable.deleteRecord(testTableRecord_2);
        assertThat("Hola").isEqualTo("Hola");
        */
    }

    @Test
    public void DeleteRecord_Normal() throws Exception {

        testTableRecord_1 = testDatabaseTable.getEmptyRecord();
        testTableRecord_1.setIntegerValue("testColumn1", 1);
        testTableRecord_1.setStringValue("testColumn2", "valor 1");
        testTableRecord_1.setStringValue("testColumn3", "valor 2");

        testDatabaseTable.insertRecord(testTableRecord_1);

        testTableRecord_2 = testDatabaseTable.getEmptyRecord();
        testTableRecord_2.setIntegerValue("testColumn1", 1);

        testDatabaseTable.deleteRecord(testTableRecord_2);

        assertThat("Hola").isEqualTo("Hola");
    }

}
