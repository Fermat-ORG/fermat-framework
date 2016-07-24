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
public class EqualsAndHashTest {

    private AndroidPluginTextFile testFile1;
    private AndroidPluginTextFile testFile2;

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

        testFile1 = constructAndroidPluginTextFile(testId, testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
    }

    @Test
    public void SameValues_Equals_SameHash() {
        testFile2 = constructAndroidPluginTextFile(testId, testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(testFile1).isEqualTo(testFile2);
        assertThat(testFile1.hashCode()).isEqualTo(testFile2.hashCode());
    }

    @Test
    public void DifferentDirectory_NotEquals_DifferentHash() {
        testFile2 = constructAndroidPluginTextFile(testId, testContext, testDirectory + "abc", testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(testFile1).isNotEqualTo(testFile2);
        assertThat(testFile1.hashCode()).isNotEqualTo(testFile2.hashCode());
    }

    @Test
    public void DifferentFileName_NotEquals_DifferentHash() {
        testFile2 = constructAndroidPluginTextFile(testId, testContext, testDirectory, testFileName + "abc", testPrivacyLevel, testLifeSpan);
        assertThat(testFile1).isNotEqualTo(testFile2);
        assertThat(testFile1.hashCode()).isNotEqualTo(testFile2.hashCode());
    }

    @Test
    public void DifferentPrivacyLevel_NotEquals_DifferentHash() {
        testFile2 = constructAndroidPluginTextFile(testId, testContext, testDirectory, testFileName, FilePrivacy.PRIVATE, testLifeSpan);
        assertThat(testFile1).isNotEqualTo(testFile2);
        assertThat(testFile1.hashCode()).isNotEqualTo(testFile2.hashCode());
    }

    private AndroidPluginTextFile constructAndroidPluginTextFile(final UUID ownerId, final String context, final String directoryName, final String fileName, final FilePrivacy privacyLevel, final FileLifeSpan lifeSpan) {
        return new AndroidPluginTextFile(ownerId, context, directoryName, fileName, privacyLevel, lifeSpan);
    }

}
