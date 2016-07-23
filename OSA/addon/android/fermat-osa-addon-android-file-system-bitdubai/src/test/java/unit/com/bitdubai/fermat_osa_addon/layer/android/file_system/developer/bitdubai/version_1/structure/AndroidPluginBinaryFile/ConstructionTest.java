package unit.com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginBinaryFile;

import android.app.Activity;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginBinaryFile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by jorgegonzalez on 2015.06.29..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ConstructionTest {

    private AndroidPluginBinaryFile testBinaryFile;

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
        testFileName = "TESTFILE.dat";
        testPrivacyLevel = FilePrivacy.PUBLIC;
        testLifeSpan = FileLifeSpan.TEMPORARY;
    }

    @Test
    public void Constructor_ValidValues_ObjectCreated() {
        testBinaryFile = new AndroidPluginBinaryFile(testId, testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(testBinaryFile).isNotNull();
    }

    @Test
    public void Constructor_ValidValues_ValuesCanBeGet() {
        testBinaryFile = new AndroidPluginBinaryFile(testId, testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(testBinaryFile.getOwnerId()).isEqualTo(testId);
        assertThat(testBinaryFile.getDirectoryName()).isEqualTo(testDirectory);
        assertThat(testBinaryFile.getFileName()).isEqualTo(testFileName);
        assertThat(testBinaryFile.getPrivacyLevel()).isEqualTo(testPrivacyLevel);
        assertThat(testBinaryFile.getLifeSpan()).isEqualTo(testLifeSpan);
    }
}
