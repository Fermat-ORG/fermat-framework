package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidPluginDatabaseSystem;

import android.app.Activity;
import android.content.Context;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
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
public class DeleteDatabaseTest {
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
    public void deleteDatabaseTest_ThrowCantOpenDatabaseException() throws Exception {
        testDatabase.createDatabase(ownerId, testDatabaseName);
        catchException(testDatabase).deleteDatabase(ownerId, "db");

        assertThat(caughtException()).isInstanceOf(CantOpenDatabaseException.class);
    }


    @Test
    public void deleteDatabaseTest_deleteOk() throws Exception {
        testDatabase.createDatabase(ownerId, testDatabaseName);

        catchException(testDatabase).deleteDatabase(ownerId, testDatabaseName);
        assertThat(caughtException()).isNull();

    }


}


