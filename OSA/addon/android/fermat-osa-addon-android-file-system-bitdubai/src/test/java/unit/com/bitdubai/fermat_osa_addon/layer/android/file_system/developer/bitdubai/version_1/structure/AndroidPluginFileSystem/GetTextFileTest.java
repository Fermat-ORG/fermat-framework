package unit.com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginFileSystem;

import android.app.Activity;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginFileSystem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.*;
import static org.fest.assertions.api.Assertions.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by jorgegonzalez on 2015.07.01..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class GetTextFileTest {

    private AndroidPluginFileSystem testFileSystem;

    private PluginTextFile testFile1;
    private PluginTextFile testFile2;

    private UUID testId;
    private String testContext;
    private String testDirectory;
    private String testFileName;
    private FilePrivacy testPrivacyLevel;
    private FileLifeSpan testLifeSpan;

    @Before
    public void setUpValues() {
        testId = UUID.randomUUID();
        Activity mockActivity = Robolectric.setupActivity(Activity.class);
        testContext = shadowOf(mockActivity).getApplicationContext().getFilesDir().getPath();
        testDirectory = "ROBOLECTRICTEST";
        testFileName = "TESTFILE.txt";
        testPrivacyLevel = FilePrivacy.PUBLIC;
        testLifeSpan = FileLifeSpan.TEMPORARY;
    }

    @Test
    public void GetTextFile_FileExists_TheFileIsLoaded() throws Exception {
        testFileSystem = new AndroidPluginFileSystem(testContext);
        testFile1 = testFileSystem.createTextFile(testId, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        testFile1.persistToMedia();

        testFile2 = testFileSystem.getTextFile(testId, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(testFile2).isNotNull();
        assertThat(testFile2).isEqualTo(testFile1);
    }

    @Test
    public void GetTextFile_FileDoesntExists_ThrowsException() throws Exception {
        testFileSystem = new AndroidPluginFileSystem(testContext);
        catchException(testFileSystem).getTextFile(testId, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(caughtException()).isNotNull();
        caughtException().printStackTrace();
    }

    @Test
    public void GetTextFile_NoContext_ThrowsException() throws Exception {
        testFileSystem = new AndroidPluginFileSystem(null);
        catchException(testFileSystem).getTextFile(testId, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(caughtException()).isNotNull();
        caughtException().printStackTrace();
    }
}
