package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinPlatformCryptoVaultPluginRoot;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by rodrigo on 2015.07.05..
 */
public class setLoggingLevelPerClassTest {
    static final LogLevel logLevel = LogLevel.AGGRESSIVE_LOGGING;
    static final String classToTest = "Prueba";
    @Test
    public void test(){
        BitcoinPlatformCryptoVaultPluginRoot root = new BitcoinPlatformCryptoVaultPluginRoot();
        Map<String, LogLevel> c = new HashMap<String, LogLevel>() ;
        c.put(classToTest, logLevel);
        /**
         * I save the Log level for class Test
         */
        root.setLoggingLevelPerClass(c);

        /**
         * I retrieved the LogLevel for class Test.
         */
        assertEquals(BitcoinPlatformCryptoVaultPluginRoot.getLogLevelByClass(classToTest), logLevel );

        LogLevel newLogLevel = LogLevel.MINIMAL_LOGGING;
        c.clear();
        c.put(classToTest, newLogLevel);
        root.setLoggingLevelPerClass(c);

        /**
         * I retrieved the LogLevel for class Test.
         */
        assertEquals(BitcoinPlatformCryptoVaultPluginRoot.getLogLevelByClass(classToTest), newLogLevel);
    }

}
