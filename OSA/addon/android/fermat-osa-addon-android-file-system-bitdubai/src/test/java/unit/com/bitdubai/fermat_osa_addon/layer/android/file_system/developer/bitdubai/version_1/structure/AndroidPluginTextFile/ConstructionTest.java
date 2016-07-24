package unit.com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginTextFile;

import android.app.Activity;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginTextFile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by jorgegonzalez on 2015.06.29..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ConstructionTest {

    private AndroidPluginTextFile testTextFile;

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
    public void Constructor_ValidValues_ObjectCreated() {
        testTextFile = new AndroidPluginTextFile(testId, testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(testTextFile).isNotNull();
    }

    @Test
    public void Constructor_ValidValues_ValuesCanBeGet() {
        testTextFile = new AndroidPluginTextFile(testId, testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(testTextFile.getOwnerId()).isEqualTo(testId);
        assertThat(testTextFile.getDirectoryName()).isEqualTo(testDirectory);
        assertThat(testTextFile.getFileName()).isEqualTo(testFileName);
        assertThat(testTextFile.getPrivacyLevel()).isEqualTo(testPrivacyLevel);
        assertThat(testTextFile.getLifeSpan()).isEqualTo(testLifeSpan);
    }
}
