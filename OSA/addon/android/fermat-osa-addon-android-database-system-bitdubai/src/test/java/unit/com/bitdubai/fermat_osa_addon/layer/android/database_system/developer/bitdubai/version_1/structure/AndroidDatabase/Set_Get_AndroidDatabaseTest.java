package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import android.app.Activity;

import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 6/8/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class)
public class Set_Get_AndroidDatabaseTest {

    private Activity mockActivity;
    private String mockContext;

    private AndroidDatabase testDatabase;
    private String testDatabaseName = "testDatabase";
    private String testDatabaseName_cambio = "testDatabaseName_cambio";

    @Before
    public void up_Set_Get_AndroidDatabase() {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = "test1"; //shadowOf(mockActivity).getApplicationContext();

        testDatabase = new AndroidDatabase(mockContext, testDatabaseName);
    }

    @Test
    public void name_AndroidDatabase() {
        assertThat(testDatabase.getDatabaseName()).isEqualTo(testDatabaseName_cambio);
    }
}