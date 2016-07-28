package unit.com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginTextFile;

import android.app.Activity;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginTextFile;

import org.junit.Before;
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
 * Created by jorgegonzalez on 2015.06.29..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoadFromMediaTest {

    private AndroidPluginTextFile testTextFile1;
    private AndroidPluginTextFile testTextFile2;

    private UUID testId;
    private String testContext;
    private String testDirectory;
    private String testFileName;
    private FilePrivacy testPrivacyLevel;
    private FileLifeSpan testLifeSpan;

    @Before
    public void setUpValues() throws Exception {
        testId = UUID.randomUUID();
        Activity mockActivity = Robolectric.setupActivity(Activity.class);
        testContext = shadowOf(mockActivity).getApplicationContext().getFilesDir().getPath();
        testDirectory = "ROBOLECTRICTEST";
        testFileName = "TESTFILE.txt";
        testPrivacyLevel = FilePrivacy.PUBLIC;
        testLifeSpan = FileLifeSpan.TEMPORARY;
        testTextFile1 = new AndroidPluginTextFile(testId, testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        testTextFile1.setContent("TEST CONTENT");
        testTextFile1.persistToMedia();
    }

    @Test
    public void LoadFromMedia_ValidValues_SameContent() throws Exception {
        testTextFile2 = new AndroidPluginTextFile(testId, testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        testTextFile2.loadFromMedia();
        assertThat(testTextFile2.getContent()).isEqualTo(testTextFile1.getContent());
    }

    @Test
    public void LoadFromMedia_FileDoesnExist_ThrowsCantLoadFileException() throws Exception {
        testTextFile2 = new AndroidPluginTextFile(testId, testContext, testDirectory, "ANOTHER" + testFileName, testPrivacyLevel, testLifeSpan);
        catchException(testTextFile2).loadFromMedia();
        assertThat(caughtException()).isInstanceOf(CantLoadFileException.class);
        caughtException().printStackTrace();
    }

    @Test
    public void LoadFromMediaDifferentOwnerId_ThrowsCantLoadFileException() throws Exception {
        testTextFile2 = new AndroidPluginTextFile(UUID.randomUUID(), testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        catchException(testTextFile2).loadFromMedia();
        assertThat(caughtException()).isInstanceOf(CantLoadFileException.class);
        caughtException().printStackTrace();
    }
}
