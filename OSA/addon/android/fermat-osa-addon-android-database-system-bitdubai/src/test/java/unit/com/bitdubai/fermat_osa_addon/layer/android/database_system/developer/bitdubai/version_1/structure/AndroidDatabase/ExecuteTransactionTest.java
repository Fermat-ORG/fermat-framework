package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import static com.googlecode.catchexception.CatchException.*;
import static org.fest.assertions.api.Assertions.*;

import android.app.Activity;
import android.content.Context;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import static org.robolectric.Shadows.shadowOf;
import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;

/**
 * Created by jorgegonzalez on 2015.06.30..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class)
public class ExecuteTransactionTest {

    private Activity mockActivity;
    private Context mockContext;

    private AndroidDatabase testDatabase;
    private String testDatabaseName = "testDatabase";
    private UUID testOwnerId;

    @Before
    public void setUp() throws Exception{
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();
        testOwnerId = UUID.randomUUID();
        testDatabase = new AndroidDatabase(mockContext, testOwnerId, testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
    }

    @Test
    public void executeTransaction_NullTransaction_ThrowsDatabaseTransactionFailed() throws Exception{
        catchException(testDatabase).executeTransaction(null);
        assertThat(caughtException()).isInstanceOf(DatabaseTransactionFailedException.class);
        caughtException().printStackTrace();
    }
}
