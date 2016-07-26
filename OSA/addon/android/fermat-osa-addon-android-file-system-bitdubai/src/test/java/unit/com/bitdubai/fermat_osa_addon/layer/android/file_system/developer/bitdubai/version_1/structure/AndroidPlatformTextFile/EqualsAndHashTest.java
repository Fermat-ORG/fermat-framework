package unit.com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPlatformTextFile;

import android.app.Activity;
import android.content.Context;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPlatformTextFile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;


/**
 * Created by jorgegonzalez on 2015.06.29..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class EqualsAndHashTest {

    private AndroidPlatformTextFile testFile1;
    private AndroidPlatformTextFile testFile2;

    private Context testContext;
    private String testContextPath;
    private String testDirectory;
    private String testFileName;
    private FilePrivacy testPrivacyLevel;
    private FileLifeSpan testLifeSpan;

    @Before
    public void setUpValues() {
        Activity mockActivity = Robolectric.setupActivity(Activity.class);
        testContext = shadowOf(mockActivity).getApplicationContext();
        testContextPath = shadowOf(mockActivity).getApplicationContext().getFilesDir().getPath();
        testDirectory = "ROBOLECTRICTEST";
        testFileName = "TESTFILE.txt";
        testPrivacyLevel = FilePrivacy.PUBLIC;
        testLifeSpan = FileLifeSpan.TEMPORARY;

        testFile1 = constructAndroidPlatformTextFile(testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
    }

    @Test
    public void SameValues_Equals_SameHash() {
        testFile2 = constructAndroidPlatformTextFile(testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(testFile1).isEqualTo(testFile2);
        assertThat(testFile1.hashCode()).isEqualTo(testFile2.hashCode());
    }

    @Test
    public void DifferentDirectory_NotEquals_DifferentHash() {
        testFile2 = constructAndroidPlatformTextFile(testContext, testDirectory + "abc", testFileName, testPrivacyLevel, testLifeSpan);
        assertThat(testFile1).isNotEqualTo(testFile2);
        assertThat(testFile1.hashCode()).isNotEqualTo(testFile2.hashCode());
    }

    @Test
    public void DifferentFileName_NotEquals_DifferentHash() {
        testFile2 = constructAndroidPlatformTextFile(testContext, testDirectory, testFileName + "abc", testPrivacyLevel, testLifeSpan);
        assertThat(testFile1).isNotEqualTo(testFile2);
        assertThat(testFile1.hashCode()).isNotEqualTo(testFile2.hashCode());
    }

    @Test
    public void DifferentPrivacyLevel_NotEquals_DifferentHash() {
        testFile2 = constructAndroidPlatformTextFile(testContext, testDirectory, testFileName, FilePrivacy.PRIVATE, testLifeSpan);
        assertThat(testFile1).isNotEqualTo(testFile2);
        assertThat(testFile1.hashCode()).isNotEqualTo(testFile2.hashCode());
    }

    private AndroidPlatformTextFile constructAndroidPlatformTextFile(final Context context, final String directoryName, final String fileName, final FilePrivacy privacyLevel, final FileLifeSpan lifeSpan) {
        return new AndroidPlatformTextFile(testContextPath, directoryName, fileName, privacyLevel, lifeSpan);
    }

}
