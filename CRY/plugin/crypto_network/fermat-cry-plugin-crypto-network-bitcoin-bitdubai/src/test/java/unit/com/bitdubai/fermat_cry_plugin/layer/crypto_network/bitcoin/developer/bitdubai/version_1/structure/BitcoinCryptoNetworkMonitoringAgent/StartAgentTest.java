package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkMonitoringAgent;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkMonitoringAgent;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.params.RegTestParams;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.07..
 */
public class StartAgentTest {

    static final NetworkParameters NETWORK_PARAMETERS = RegTestParams.get();
    static final String userPublicKey = new ECCKeyPair().getPublicKey();
    @Mock
    LogManager logManager;
    
    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    ErrorManager errorManager;

    @Test
    public void startTest(){
        CryptoVault cryptoVault = new CryptoVault() {
            Wallet vault = new Wallet(NETWORK_PARAMETERS );
            @Override
            public void setUserPublicKey(String userPublicKey) {

            }

            @Override
            public String getUserPublicKey() {
                return userPublicKey;
            }

            @Override
            public Object getWallet() {
                return vault;
            }
        };

        BitcoinCryptoNetworkMonitoringAgent agent = new BitcoinCryptoNetworkMonitoringAgent((Wallet) cryptoVault.getWallet(), userPublicKey);

        agent.setErrorManager(errorManager);
        agent.setLogManager(logManager);

        agent.setPluginFileSystem(pluginFileSystem);

        agent.setPluginId(UUID.randomUUID());
    }

}
