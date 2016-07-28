package unit.com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginTextFile;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginFileSystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

/**
 * The class <code>unit.com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginTextFile.EncryptAndDecryptTest</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/11/2015.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = android.support.v13.BuildConfig.class)
public class EncryptAndDecryptTest {

    private static final String PLUGIN_IDS_DIRECTORY_NAME = "lnacosta";
    private static final String PLUGIN_IDS_FILE_NAME = "walletsIds";

    @Test
    public void constructorIntOk() throws Exception {
        try {
            UUID uuid = UUID.randomUUID();

            AndroidPluginFileSystem fileSystem = new AndroidPluginFileSystem("/home");

            fileSystem.createTextFile(
                    uuid,
                    PLUGIN_IDS_DIRECTORY_NAME,
                    PLUGIN_IDS_FILE_NAME,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void asdsad() throws Exception {
        try {
            UUID uuid = UUID.randomUUID();
            ECCKeyPair eccKeyPair = new ECCKeyPair();
            String content = eccKeyPair.getPrivateKey() + ";" + eccKeyPair.getPublicKey();

            AndroidPluginFileSystem fileSystem = new AndroidPluginFileSystem("/home");

            PluginTextFile asd = fileSystem.createTextFile(
                    uuid,
                    PLUGIN_IDS_DIRECTORY_NAME,
                    PLUGIN_IDS_FILE_NAME,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            asd.setContent(content);

            asd.persistToMedia();

            asd = fileSystem.getTextFile(
                    uuid,
                    PLUGIN_IDS_DIRECTORY_NAME,
                    PLUGIN_IDS_FILE_NAME,
                    FilePrivacy.PRIVATE,
                    FileLifeSpan.PERMANENT
            );

            System.out.println(asd.getContent());

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}