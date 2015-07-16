package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTable;

import android.app.Activity;
import android.content.Context;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by natalia on 15/07/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class deleteRecordTest {


    private Activity mockActivity;
    private Context mockContext;

    private AndroidDatabase testDatabase;
    private DatabaseTable testDatabaseTable;
    private UUID testOwnerId;
    private String testDatabaseName = "testDatabase";

    private String testTableName = "testTable";

    private DatabaseTableFactory testTableFactory;

    private DatabaseTableRecord testTableRecord;

    @Before
    public  void setUpDatabase() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();
        testOwnerId = UUID.randomUUID();
        testDatabase = new AndroidDatabase(mockContext, testOwnerId, testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
        testDatabaseTable = testDatabase.getTable(testTableName);
        testDatabaseTable.loadToMemory();
        testTableRecord = testDatabaseTable.getEmptyRecord();
        testTableRecord.setIntegerValue("testColumn1", 1);
        testTableRecord.setStringValue("testColumn2", "prueba");

    }

    @Before
    public void setUpTableFactory(){
        testTableFactory = new AndroidDatabaseTableFactory(testTableName);
        testTableFactory.addColumn("testColumn1", DatabaseDataType.INTEGER, 0, false);
        testTableFactory.addColumn("testColumn2", DatabaseDataType.STRING, 10, false);
    }


    @Test
    public void DeleteRecord_Succefuly_TrowsCantDeleteRecordException() throws Exception{

        testTableRecord = testDatabaseTable.getRecords().get(0);
        testTableRecord.setIntegerValue("testColumn1", 1);


        catchException(testDatabaseTable).deleteRecord(testTableRecord);
        assertThat(caughtException()).isInstanceOf(CantDeleteRecordException.class);


    }

    @Test
    public void InsertRecord_NotSuccefuly_TrowsCantDeleteRecordException() throws Exception{

        testTableRecord = testDatabaseTable.getEmptyRecord();
        testTableRecord.setIntegerValue("testColumn1", 2);

        catchException(testDatabaseTable).deleteRecord(testTableRecord);
        assertThat(caughtException()).isInstanceOf(CantDeleteRecordException.class);
    }
}


