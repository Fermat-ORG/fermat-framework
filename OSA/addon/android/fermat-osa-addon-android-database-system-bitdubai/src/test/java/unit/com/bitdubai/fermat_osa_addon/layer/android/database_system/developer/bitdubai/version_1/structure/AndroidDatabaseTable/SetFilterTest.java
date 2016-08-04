package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTable;

import android.app.Activity;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTable;
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
 * Created by natalia on 15/07/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class SetFilterTest {
    private Activity mockActivity;
    private String mockContext;

    private AndroidDatabase testDatabase;
    private DatabaseTable testDatabaseTable;
    private UUID testOwnerId;
    private String testDatabaseName = "testDatabase";

    private String testTableName = "testTable";

    private DatabaseTableFactory testTableFactory;

    @Before
    public void setUpDatabase() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = "test1"; //shadowOf(mockActivity).getApplicationContext();
        testOwnerId = UUID.randomUUID();
        testDatabase = new AndroidDatabase(mockContext, testOwnerId, testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
        testDatabaseTable = testDatabase.getTable(testTableName);


    }

    @Before
    public void setUpTableFactory() {
        testTableFactory = new AndroidDatabaseTableFactory(testTableName);
        testTableFactory.addColumn("testColumn1", DatabaseDataType.INTEGER, 0, false);
        testTableFactory.addColumn("testColumn2", DatabaseDataType.STRING, 10, false);
        testTableFactory.addColumn("testColumn3", DatabaseDataType.REAL, 10, false);
    }


    @Test
    public void setFilterString_Succefuly() throws Exception {
        testDatabaseTable.addStringFilter("testColumn2", "nn", DatabaseFilterType.EQUAL);

    }

    @Test
    public void setFilterUUID_Succefuly() throws Exception {
        testDatabaseTable.addUUIDFilter("testColumn2", UUID.randomUUID(), DatabaseFilterType.EQUAL);

    }


    @Test
    public void constructorTest_Succefuly() throws Exception {
        testDatabaseTable = new AndroidDatabaseTable(testDatabase, "tableName");

    }
}
