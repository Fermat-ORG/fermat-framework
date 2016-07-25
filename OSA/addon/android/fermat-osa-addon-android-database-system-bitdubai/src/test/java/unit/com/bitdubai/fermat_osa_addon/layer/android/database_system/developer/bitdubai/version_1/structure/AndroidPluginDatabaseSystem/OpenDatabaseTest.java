package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPluginDatabaseSystem;

import android.app.Activity;
import android.content.Context;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPluginDatabaseSystem;

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
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by natalia on 14/09/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class OpenDatabaseTest {
    private Activity mockActivity;
    private Context mockContext;

    private AndroidPluginDatabaseSystem testDatabase;
    private String testDatabaseName = "testDatabase";
    private UUID ownerId;

    @Before
    public void setUpContext() {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();

        ownerId = UUID.randomUUID();
    }

    @Test
    public void openDatabase_DatabaseInPath_InvokedSuccesfully() throws Exception {
        testDatabase.createDatabase(ownerId, testDatabaseName);
        catchException(testDatabase).openDatabase(ownerId, testDatabaseName);
        assertThat(caughtException()).isNull();
    }

    @Test
    public void openDatabase_DatabaseInAlreadyOpen_InvokedSuccesfully() throws Exception {
        testDatabase.createDatabase(ownerId, testDatabaseName);
        testDatabase.openDatabase(ownerId, testDatabaseName);
        catchException(testDatabase).openDatabase(ownerId, testDatabaseName);
        assertThat(caughtException()).isNull();
    }

    @Test
    public void openDatabase_NoDatabaseInPath_ThrowException() throws Exception {
        catchException(testDatabase).openDatabase(ownerId, testDatabaseName);
        assertThat(caughtException()).isInstanceOf(DatabaseNotFoundException.class);

    }

    @Test
    public void openDatabase_DatabaseInPath_InvokedSuccesfully1() throws Exception {
        testDatabase.createDatabase(ownerId, testDatabaseName);
        catchException(testDatabase).openDatabase(ownerId, testDatabaseName);
        assertThat(caughtException()).isNull();

    }

}

