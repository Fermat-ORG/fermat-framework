package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPlatformDatabaseSystem;

import android.app.Activity;
import android.content.Context;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
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
@Config(constants = CustomBuildConfig.class)
public class CreateDatabaseTest {

    private Activity mockActivity;
    private Context mockContext;

    private AndroidPlatformDatabaseSystem testDatabase;
    private String testDatabaseName = "testDatabase";


    @Before
    public void createDatabase_TheDatabaseHasNotBeenCreated_MethodInvokedSuccessfully() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();

        catchException(testDatabase).createDatabase(testDatabaseName);
        assertThat(caughtException()).isNull();
    }

    @Test
    public void createDatabase_TheDatabaseHasAlreadyBeenCreated_ThrowsCantCreateDatabaseException() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);

        catchException(testDatabase).createDatabase(testDatabaseName);
        assertThat(caughtException()).isInstanceOf(CantCreateDatabaseException.class);

    }
}

