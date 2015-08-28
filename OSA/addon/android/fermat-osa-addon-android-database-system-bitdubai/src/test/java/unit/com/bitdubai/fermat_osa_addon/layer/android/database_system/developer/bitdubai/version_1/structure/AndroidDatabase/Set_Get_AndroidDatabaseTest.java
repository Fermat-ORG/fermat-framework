package AndroidDatabase;

import android.app.Activity;
import android.content.Context;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;
import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;

/**
 * Created by angel on 6/8/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class)
public class Set_Get_AndroidDatabaseTest {

    private Activity mockActivity;
    private Context mockContext;

    private AndroidDatabase testDatabase;
    private String testDatabaseName = "testDatabase";
    private String testDatabaseName_cambio = "testDatabaseName_cambio";

    private DatabaseTransaction transaction_1;

    @Before
    public void Up_Set_Get_AndroidDatabase(){
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();

        testDatabase = new AndroidDatabase(mockContext, testDatabaseName);

        testDatabase.setDatabaseName(testDatabaseName_cambio);
        transaction_1 = testDatabase.newTransaction();

        testDatabase.setDatabaseTransaction(transaction_1);
    }

    @Test
    public void Name_AndroidDatabase(){
        assertThat(testDatabase.getDatabaseName()).isEqualTo(testDatabaseName_cambio);
    }

    @Test
    public void Transaction_AndroidDatabase(){
        assertThat(testDatabase.getDatabaseTransaction()).isEqualTo(transaction_1);
    }

    @Test
    public void Get_Table_AndroidDatabase(){
        assertThat(testDatabase.getDatabaseTransaction()).isEqualTo(transaction_1);
    }
}