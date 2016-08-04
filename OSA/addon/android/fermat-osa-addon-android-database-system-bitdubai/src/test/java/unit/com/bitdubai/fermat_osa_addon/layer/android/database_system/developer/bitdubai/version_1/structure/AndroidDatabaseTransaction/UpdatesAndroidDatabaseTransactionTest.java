package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTransaction;

import android.app.Activity;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseRecord;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFactory;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTransaction;

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
 * Created by angel on 28/7/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class)
public class UpdatesAndroidDatabaseTransactionTest {

    private Activity mockActivity;
    private String mockContext;

    private AndroidDatabase testDatabase;
    private UUID testOwnerId;
    private String testDatabaseName = "testDatabase";
    private DatabaseTableFactory testTableFactory;


    private DatabaseTable tabla_1, tabla_2;
    private AndroidDatabaseRecord record_1, record_2;
    private AndroidDatabaseTransaction transaction_1, transaction_2;

    public void setUpDatabase() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = "test1"; //shadowOf(mockActivity).getApplicationContext();
        testOwnerId = UUID.randomUUID();
        testDatabase = new AndroidDatabase(mockContext, testOwnerId, testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
    }

    public void setUpTable() throws Exception {

        testTableFactory = new AndroidDatabaseTableFactory("tabla_1");
        testTableFactory.addColumn("testColumn1", DatabaseDataType.INTEGER, 0, true);
        testTableFactory.addColumn("testColumn2", DatabaseDataType.STRING, 10, false);
        testTableFactory.addIndex("testColumn1");
        testDatabase.createTable(testTableFactory);


        testTableFactory = new AndroidDatabaseTableFactory("tabla_2");
        testTableFactory.addColumn("testColumn1", DatabaseDataType.INTEGER, 0, true);
        testTableFactory.addColumn("testColumn2", DatabaseDataType.STRING, 10, false);
        testTableFactory.addColumn("testColumn3", DatabaseDataType.STRING, 10, false);
        testTableFactory.addIndex("testColumn1");
        testDatabase.createTable(testTableFactory);

        tabla_1 = testDatabase.getTable("tabla_1");
        tabla_2 = testDatabase.getTable("tabla_2");

        record_1 = new AndroidDatabaseRecord();
        record_2 = new AndroidDatabaseRecord();

        transaction_1 = new AndroidDatabaseTransaction();
        transaction_1.addRecordToUpdate(tabla_1, record_1);
    }

    @Before
    public void setUp() throws Exception {
        setUpDatabase();
        setUpTable();
    }

    @Test
    public void Updates_AreEquals() {
        transaction_2 = new AndroidDatabaseTransaction();
        transaction_2.addRecordToUpdate(tabla_1, record_1);
        assertThat(transaction_1.getRecordsToUpdate()).isEqualTo(transaction_2.getRecordsToUpdate());
        assertThat(transaction_1.getTablesToUpdate()).isEqualTo(transaction_2.getTablesToUpdate());
    }

    @Test
    public void Updates_NotEquals() {
        transaction_2 = new AndroidDatabaseTransaction();
        transaction_2.addRecordToUpdate(tabla_2, record_2);
        assertThat(transaction_1.getRecordsToUpdate()).isNotEqualTo(transaction_2.getRecordsToUpdate());
        assertThat(transaction_1.getTablesToUpdate()).isNotEqualTo(transaction_2.getTablesToUpdate());
    }

}
