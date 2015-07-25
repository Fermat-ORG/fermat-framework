package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure.BitcoinCryptoNetworkMonitoringAgent;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVault;
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
    static final UUID userId = UUID.randomUUID();
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
            public void setUserId(UUID UserId) {

            }

            @Override
            public UUID getUserId() {
                return userId;
            }

            @Override
            public Object getWallet() {
                return vault;
            }
        };
        BitcoinCryptoNetworkMonitoringAgent agent = new BitcoinCryptoNetworkMonitoringAgent((Wallet) cryptoVault.getWallet(), UUID.randomUUID());
        agent.setErrorManager(errorManager);
        agent.setLogManager(logManager);
        agent.setPluginFileSystem(pluginFileSystem);
        agent.setPluginId(UUID.randomUUID());
    }

}
