package unit.com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginBinaryFile;

import android.app.Activity;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginBinaryFile;

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
public class PersistToMediaTest {

    private static final String CHARSET_NAME = "UTF-8";

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
    public void PersistToMedia_ValidValues_MethodInvokedSuccesFully() throws Exception {
        testBinaryFile = new AndroidPluginBinaryFile(testId, testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        testBinaryFile.setContent("TEST BYTES".getBytes(CHARSET_NAME));
        catchException(testBinaryFile).persistToMedia();
        assertThat(caughtException()).isNull();
    }

    @Test
    public void PersistToMedia_NoContent_ThrowsCantPersistFileException() throws Exception {
        testBinaryFile = new AndroidPluginBinaryFile(testId, testContext, testDirectory, testFileName, testPrivacyLevel, testLifeSpan);
        catchException(testBinaryFile).persistToMedia();
        assertThat(caughtException()).isInstanceOf(CantPersistFileException.class);
        caughtException().printStackTrace();
    }

    @Test
    public void PersistToMedia_InValidFileName_ThrowsCantPersistFileExceptionException() throws Exception {
        testBinaryFile = new AndroidPluginBinaryFile(testId, testContext, testDirectory, "", testPrivacyLevel, testLifeSpan);
        testBinaryFile.setContent("TEST BYTES".getBytes(CHARSET_NAME));
        catchException(testBinaryFile).persistToMedia();
        assertThat(caughtException()).isInstanceOf(CantPersistFileException.class);
        caughtException().printStackTrace();
    }
}
