package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTable;

import android.app.Activity;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 15/07/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class InsertRecordTest {


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
        mockContext = "test1"; //shadowOf(mockActivity).getApplicationContext();
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

    @Before
    public void setUp() throws Exception {
        setUpDatabase();
        setUpTable();
    }

    @Test
    public void InsertRecord_SuccesfulyInserted_RecordInTheLoadedRecordsList() throws Exception {

        testTableRecord = testDatabaseTable.getEmptyRecord();
        testTableRecord.setIntegerValue("testColumn1", 1);
        testTableRecord.setStringValue("testColumn2", "prueba");

        testDatabaseTable.insertRecord(testTableRecord);

        testDatabaseTable.loadToMemory();
        DatabaseTableRecord testRecord = testDatabaseTable.getRecords().get(0);

        assertThat(testRecord.getIntegerValue("testColumn1")).isEqualTo(testTableRecord.getIntegerValue("testColumn1"));
        assertThat(testRecord.getStringValue("testColumn2")).isEqualTo(testTableRecord.getStringValue("testColumn2"));
    }

    //TODO CHECK WHY THIS DOESN'T WORK
    @Ignore
    @Test
    public void InsertRecord_DuplicatePrimaryKey_TrowsCantInsertRecordExceptionException() throws Exception {
        testTableRecord = testDatabaseTable.getEmptyRecord();
        testTableRecord.setIntegerValue("testColumn1", 1);
        testTableRecord.setStringValue("testColumn2", "prueba");

        DatabaseTableRecord testTableRecord2 = testDatabaseTable.getEmptyRecord();
        testTableRecord2.setIntegerValue("testColumn1", 1);
        testTableRecord2.setStringValue("testColumn2", "prueba");

        testDatabaseTable.insertRecord(testTableRecord);
        catchException(testDatabaseTable).insertRecord(testTableRecord2);
        assertThat(caughtException()).isInstanceOf(CantInsertRecordException.class);
    }
}
