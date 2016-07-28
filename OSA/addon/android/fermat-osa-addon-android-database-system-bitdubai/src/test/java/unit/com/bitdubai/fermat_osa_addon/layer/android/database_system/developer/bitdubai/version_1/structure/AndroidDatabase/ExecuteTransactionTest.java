package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import android.app.Activity;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
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

import static com.googlecode.catchexception.CatchException.*;
import static org.fest.assertions.api.Assertions.*;

/**
 * Created by jorgegonzalez on 2015.06.30..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class)
public class ExecuteTransactionTest {

    private Activity mockActivity;
    private String mockContext;

    private AndroidDatabase testDatabase;
    private String testDatabaseName = "testDatabase";
    private UUID testOwnerId;

    @Before
    public void setUp() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = "test1"; //shadowOf(mockActivity).getApplicationContext();
        testOwnerId = UUID.randomUUID();
        testDatabase = new AndroidDatabase(mockContext, testOwnerId, testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
    }

    @Test
    public void executeTransaction_Transactionok_ThrowsDatabaseTransactionFailed() throws Exception {
        DatabaseTableFactory testTableFactory = new AndroidDatabaseTableFactory("testTable");
        testTableFactory.addColumn("testColumn1", DatabaseDataType.INTEGER, 0, false);
        testTableFactory.addColumn("testColumn2", DatabaseDataType.STRING, 10, false);

        testDatabase.createTable(testOwnerId, testTableFactory);

        DatabaseTable dataBaseTable = testDatabase.getTable("testTable");

        DatabaseTableRecord newRecord = dataBaseTable.getEmptyRecord();
        newRecord.setIntegerValue("testColumn1", 10);
        newRecord.setStringValue("testColumn2", "column2");

        dataBaseTable.insertRecord(newRecord);

        dataBaseTable.loadToMemory();

        DatabaseTableRecord selectRecord = dataBaseTable.getRecords().get(0);

        DatabaseTableRecord updateRecord = dataBaseTable.getRecords().get(0);
        updateRecord.setStringValue("testColumn2", "columnChange");

        DatabaseTableRecord insertRecord = dataBaseTable.getEmptyRecord();

        insertRecord.setIntegerValue("testColumn1", 10);
        insertRecord.setStringValue("testColumn2", "column3");

        DatabaseTransaction databaseTransaction = testDatabase.newTransaction();

        databaseTransaction.addRecordToSelect(dataBaseTable, selectRecord);
        databaseTransaction.addRecordToUpdate(dataBaseTable, updateRecord);
        databaseTransaction.addRecordToInsert(dataBaseTable, insertRecord);

        catchException(testDatabase).executeTransaction(databaseTransaction);

        databaseTransaction.toString();
        assertThat(caughtException()).isNull();

    }

    @Test
    public void executeTransaction_NullTransaction_ThrowsDatabaseTransactionFailed() throws Exception {

        catchException(testDatabase).executeTransaction(null);

        assertThat(caughtException()).isInstanceOf(DatabaseTransactionFailedException.class);

    }
}
