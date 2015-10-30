package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by rodrigo on 2015.07.14..
 */
public class SettersTest {

    @Test
    public void testSetErrorManager(){
        BitcoinCryptoNetworkPluginRoot root  = new BitcoinCryptoNetworkPluginRoot();
        ErrorManager errorManager = mock(ErrorManager.class);
        root.setErrorManager(errorManager);
    }

    @Test
    public void testSetLogManager(){
        BitcoinCryptoNetworkPluginRoot root  = new BitcoinCryptoNetworkPluginRoot();
        LogManager logManager= mock(LogManager.class);
        root.setLogManager(logManager);
    }
}
