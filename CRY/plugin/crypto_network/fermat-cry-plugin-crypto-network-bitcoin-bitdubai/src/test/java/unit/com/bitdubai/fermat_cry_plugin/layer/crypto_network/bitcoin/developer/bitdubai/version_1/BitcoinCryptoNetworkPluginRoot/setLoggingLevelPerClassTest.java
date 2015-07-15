package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by rodrigo on 2015.07.05..
 */
public class setLoggingLevelPerClassTest {

    static final String CLASS = "ClassToTest";
    static final LogLevel LOG_LEVEL = LogLevel.MODERATE_LOGGING;

    @Test
    public void testMethod(){
        BitcoinCryptoNetworkPluginRoot root = new BitcoinCryptoNetworkPluginRoot();
        Map<String, LogLevel> data = new HashMap<String, LogLevel>();
        data.put(CLASS, LOG_LEVEL);
        root.setLoggingLevelPerClass(data);

        assertEquals(BitcoinCryptoNetworkPluginRoot.getLogLevelByClass(CLASS), LOG_LEVEL);

        data.clear();
        LogLevel newLogLevel = LogLevel.AGGRESSIVE_LOGGING;
        data.put(CLASS, newLogLevel);
        root.setLoggingLevelPerClass(data);

        assertEquals(BitcoinCryptoNetworkPluginRoot.getLogLevelByClass(CLASS), newLogLevel);

    }


}
