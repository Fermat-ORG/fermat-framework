package integration.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkMonitoringAgent;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantCreateBlockStoreFileException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkMonitoringAgent;

import org.bitcoinj.core.Wallet;
import org.bitcoinj.params.RegTestParams;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class StartAgentTest {
    @Mock ErrorManager errorManager;
    @Mock LogManager logManager;

    private String walletPublicKey = new ECCKeyPair().getPublicKey();

    @Test
    public void testRegTest() throws CantStartAgentException, InterruptedException, CantCreateBlockStoreFileException {
        Wallet wallet = new Wallet(RegTestParams.get());

        BitcoinCryptoNetworkMonitoringAgent bitcoinCryptoNetworkMonitoringAgent = new BitcoinCryptoNetworkMonitoringAgent(wallet, walletPublicKey);

        MockPluginFileSystem pluginFileSystem = new MockPluginFileSystem();
        bitcoinCryptoNetworkMonitoringAgent.setPluginFileSystem(pluginFileSystem);


        bitcoinCryptoNetworkMonitoringAgent.setErrorManager(errorManager);
        bitcoinCryptoNetworkMonitoringAgent.setLogManager(logManager);
        bitcoinCryptoNetworkMonitoringAgent.setPluginId(UUID.randomUUID());

        bitcoinCryptoNetworkMonitoringAgent.configureBlockChain();

        bitcoinCryptoNetworkMonitoringAgent.configurePeers();
        bitcoinCryptoNetworkMonitoringAgent.start();

        Thread.sleep(5000);

        Assert.assertTrue(bitcoinCryptoNetworkMonitoringAgent.isRunning());
        Assert.assertNotNull(bitcoinCryptoNetworkMonitoringAgent.getConnectedPeers());

        Assert.assertNotNull(bitcoinCryptoNetworkMonitoringAgent.getPeers());


        bitcoinCryptoNetworkMonitoringAgent.stop();
        bitcoinCryptoNetworkMonitoringAgent.start();
        bitcoinCryptoNetworkMonitoringAgent.stop();
        Assert.assertFalse(bitcoinCryptoNetworkMonitoringAgent.isRunning());
    }

    /**
     * Class that mocks the pluginFileSystem, it will return a fake file for the block chain creation.
     */
    private class MockPluginFileSystem implements PluginFileSystem {
        @Override
        public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
            return null;
        }

        @Override
        public PluginTextFile createTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {
            PluginTextFile textFile = new PluginTextFile() {
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
            return textFile;
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
