package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.StoredBlockchain;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantCreateBlockStoreFileException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.StoredBlockChain;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import org.bitcoinj.core.Wallet;
import org.bitcoinj.params.RegTestParams;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class createBlockChainTest {

    @Mock
    ErrorManager errorManager;

    private String walletPublicKey;

    @Before
    public void setUp() {
        walletPublicKey = new ECCKeyPair().getPublicKey();
    }

    @Test(expected = CantCreateBlockStoreFileException.class)
    public void genereateCantPersistFileException() throws CantCreateBlockStoreFileException {
        MockedPluginFileSystemWithError pluginFileSystem = new MockedPluginFileSystemWithError();
        Wallet wallet = new Wallet(RegTestParams.get());
        StoredBlockChain storedBlockChain = new StoredBlockChain(wallet, walletPublicKey);
        storedBlockChain.setErrorManager(errorManager);
        storedBlockChain.setPluginId(UUID.randomUUID());
        storedBlockChain.setPluginFileSystem(pluginFileSystem);

        /**
         * Im forcing the raise of the exception in the MockedPluginFileSystem class
         */
        storedBlockChain.createBlockChain();
    }

    @Test(expected = CantCreateBlockStoreFileException.class)
    public void genereateCantCreateFileException() throws CantCreateBlockStoreFileException {
        MockedPluginFileSystemWithError2 pluginFileSystem = new MockedPluginFileSystemWithError2();
        Wallet wallet = new Wallet(RegTestParams.get());
        StoredBlockChain storedBlockChain = new StoredBlockChain(wallet, walletPublicKey);
        storedBlockChain.setErrorManager(errorManager);
        storedBlockChain.setPluginId(UUID.randomUUID());
        storedBlockChain.setPluginFileSystem(pluginFileSystem);

        /**
         * Im forcing the raise of the exception in the MockedPluginFileSystem class
         */
        storedBlockChain.createBlockChain();
    }
    @Test
    public void getBlockchainTest() throws CantCreateBlockStoreFileException {
        MockedPluginFileSystem pluginFileSystem = new MockedPluginFileSystem();
        Wallet wallet = new Wallet(RegTestParams.get());
        StoredBlockChain storedBlockChain = new StoredBlockChain(wallet, walletPublicKey);
        storedBlockChain.setErrorManager(errorManager);
        storedBlockChain.setPluginId(UUID.randomUUID());
        storedBlockChain.setPluginFileSystem(pluginFileSystem);

        storedBlockChain.createBlockChain();

        Assert.assertNotNull(storedBlockChain.getBlockChain());
    }

    private class MockedPluginFileSystem implements PluginFileSystem{
        @Override
        public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
            return null;
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

    private class MockedPluginFileSystemWithError implements PluginFileSystem{
        @Override
        public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
            return null;
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
                    throw new CantPersistFileException("Mocked Error!");

                }

                @Override
                public void loadFromMedia() throws CantLoadFileException {

                }

                @Override
                public void delete() {

                }
            };
            throw new CantCreateFileException("Mocked error");
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

    private class MockedPluginFileSystemWithError2 implements PluginFileSystem{
        @Override
        public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
            return null;
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
            throw new CantCreateFileException("Mocked error");
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
}
