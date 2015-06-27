package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;

import java.io.File;
import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.06.27..
 */
public class OpenDatabaseTest {

    @Mock
    private Context mockContext = mock(Context.class);
    @Mock
    private File mockFilesDir = mock(File.class);

    private AndroidDatabase testDatabase;
    private String testDatabaseName = "testDatabase";


    @Test
    public void OpenDatabase_NoDatabaseInPath_ThrowException() throws Exception{
        when(mockContext.getFilesDir()).thenReturn(mockFilesDir);
        when(mockFilesDir.getPath()).thenReturn("/");

        testDatabase = new AndroidDatabase(mockContext, UUID.randomUUID(), testDatabaseName);

        catchException(testDatabase).openDatabase(testDatabaseName);
        caughtException().printStackTrace();
        assertThat(caughtException()).isInstanceOf(DatabaseNotFoundException.class);
    }


}
