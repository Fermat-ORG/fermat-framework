package ChatIdentityImplTest;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure.ChatIdentityImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Miguel Rincon on 4/15/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private final String mAlias = new String();
    private final String mPublicKey = new String();
    private final byte[] mProfileImage = new byte[10];
    private final String mPrivateKey = new String();
    private final PluginFileSystem mPluginFileSystem = new PluginFileSystem() {
        @Override
        public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
            return null;
        }

        @Override
        public PluginTextFile createTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {
            return null;
        }

        @Override
        public boolean isTextFileExist(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws Exception {
            return false;
        }

        @Override
        public PluginBinaryFile getBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
            return null;
        }

        @Override
        public PluginBinaryFile createBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {
            return null;
        }

        @Override
        public void deleteTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException, FileNotFoundException {

        }

        @Override
        public void deleteBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException, FileNotFoundException {

        }

        @Override
        public String getAppPath() {
            return null;
        }
    };
    private final UUID mPluginId = UUID.randomUUID();

    @Test
    public void contructionWithValidParametersNewObjectCreated() {
        ChatIdentityImpl chatIdentity = new ChatIdentityImpl(
                this.mAlias,
                this.mPublicKey,
                this.mPrivateKey,
                this.mProfileImage,
                this.mPluginFileSystem,
                this.mPluginId
        );
        assertThat(chatIdentity).isNotNull();
    }

}
