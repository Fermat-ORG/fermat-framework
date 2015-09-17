package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by franklin on 05/08/15.
 */
public class SetLoggingLevelPerClassTest {
    static final String CLASS = "ClassToTest";
    static final LogLevel LOG_LEVEL = LogLevel.MODERATE_LOGGING;

    @Test
    public void testMethod(){
        IncomingCryptoTransactionPluginRoot root = new IncomingCryptoTransactionPluginRoot();
        Map<String, LogLevel> data = new HashMap<String, LogLevel>();
        data.put(CLASS, LOG_LEVEL);
        root.setLoggingLevelPerClass(data);

        assertEquals(IncomingCryptoTransactionPluginRoot.getLogLevelByClass(CLASS), LOG_LEVEL);

        data.clear();
        LogLevel newLogLevel = LogLevel.AGGRESSIVE_LOGGING;
        data.put(CLASS, newLogLevel);
        root.setLoggingLevelPerClass(data);

        assertEquals(IncomingCryptoTransactionPluginRoot.getLogLevelByClass(CLASS), newLogLevel);

    }
}
