package test.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVault;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import junit.framework.Assert;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.params.RegTestParams;
import org.junit.Test;
import org.junit.Ignore;
import org.mockito.Mock;

import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.05..
 */
public class connectToBitcoinNetworkTest {
    static final NetworkParameters NETWORK_PARAMETERS = RegTestParams.get();
    static final UUID userId = UUID.randomUUID();
    @Mock
    LogManager logManager;


    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    ErrorManager errorManager;


    @Test(expected=NullPointerException.class)
    public void connectTest() throws CantConnectToBitcoinNetwork {

        BitcoinCryptoNetworkPluginRoot root = new BitcoinCryptoNetworkPluginRoot();

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

            root.setId(UUID.randomUUID());
            root.setVault(cryptoVault);
            root.setPluginFileSystem(pluginFileSystem);
            root.setErrorManager(errorManager);
            root.setLogManager(logManager);
            root.connectToBitcoinNetwork();


    }


}
