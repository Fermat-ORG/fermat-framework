package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.15..
 */
public class MockedPluginFileSystem implements PluginFileSystem{
    @Override
    public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
        PluginTextFile pluginTextFile = new PluginTextFile() {
            @Override
            public String getContent() {
                return null;
            }

            @Override
            public void setContent(String content) {

            }

            @Override
            public void persistToMedia() throws CantPersistFileException {

            }

            @Override
            public void loadFromMedia() throws CantLoadFileException {

            }

            @Override
            public void delete() {

            }
        };
        return pluginTextFile;
    }

    @Override
    public PluginTextFile createTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {
        PluginTextFile pluginTextFile = new PluginTextFile() {
            @Override
            public String getContent() {
                return null;
            }

            @Override
            public void setContent(String content) {

            }

            @Override
            public void persistToMedia() throws CantPersistFileException {

            }

            @Override
            public void loadFromMedia() throws CantLoadFileException {

            }

            @Override
            public void delete() {

            }
        };
        return pluginTextFile;
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
    public void setContext(Object context) {

    }
}
