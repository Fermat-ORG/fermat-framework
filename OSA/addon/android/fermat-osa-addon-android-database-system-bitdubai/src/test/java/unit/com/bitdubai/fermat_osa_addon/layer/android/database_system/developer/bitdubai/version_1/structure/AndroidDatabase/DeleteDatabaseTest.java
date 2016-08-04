package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import android.app.Activity;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import org.junit.Before;
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
 * Created by jorgegonzalez on 2015.06.27..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class)
public class DeleteDatabaseTest {

    private Activity mockActivity;
    private String mockContext;

    private AndroidDatabase testDatabase;
    private String testDatabaseName = "testDatabase";

    @Before
    public void setUpContext() {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = "test1"; //shadowOf(mockActivity).getApplicationContext();
    }

    @Test
    public void DeleteDatabase_DatabaseInPath_InvokedSuccesfully() throws Exception {
        testDatabase = new AndroidDatabase(mockContext, UUID.randomUUID(), testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
        catchException(testDatabase).deleteDatabase();
        assertThat(caughtException()).isNull();
    }

    @Test
    public void DeleteDatabase_NoDatabaseInPath_ThrowException() throws Exception {
        testDatabase = new AndroidDatabase(mockContext, UUID.randomUUID(), testDatabaseName);
        catchException(testDatabase).deleteDatabase();
        caughtException().printStackTrace();
        assertThat(caughtException()).isInstanceOf(DatabaseNotFoundException.class);
    }

}
