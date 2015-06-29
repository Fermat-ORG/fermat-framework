package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import android.app.Activity;
import android.content.Context;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by jorgegonzalez on 2015.06.27..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CreateDatabaseTest {

    private Activity mockActivity;
    private Context mockContext;

    private AndroidDatabase testDatabase;
    private String testDatabaseName = "testDatabase";


    @Test
    public void DeleteDatabase_NoDatabaseInPath_ThrowException() throws Exception{
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();

        testDatabase = new AndroidDatabase(mockContext, UUID.randomUUID(), testDatabaseName);
        catchException(testDatabase).deleteDatabase("abcdefgh");
        caughtException().printStackTrace();
        assertThat(caughtException()).isInstanceOf(DatabaseNotFoundException.class);
    }

}
