package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPlatformDatabaseSystem;

import android.app.Activity;
import android.content.Context;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPlatformDatabaseSystem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

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
public class DeleteDatabaseTest {
    private Activity mockActivity;
    private Context mockContext;

    private AndroidPlatformDatabaseSystem testDatabase;
    private String testDatabaseName = "testDatabase";


    @Before
    public void setUpContext() {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();

    }

    @Test
    public void deleteDatabaseTest_ThrowCantOpenDatabaseException() throws Exception {
        testDatabase.createDatabase(testDatabaseName);
        catchException(testDatabase).deleteDatabase("db");

        assertThat(caughtException()).isInstanceOf(CantOpenDatabaseException.class);
    }


    @Test
    public void deleteDatabaseTest_deleteOk() throws Exception {
        testDatabase.createDatabase(testDatabaseName);

        catchException(testDatabase).deleteDatabase(testDatabaseName);
        assertThat(caughtException()).isNull();

    }


}


