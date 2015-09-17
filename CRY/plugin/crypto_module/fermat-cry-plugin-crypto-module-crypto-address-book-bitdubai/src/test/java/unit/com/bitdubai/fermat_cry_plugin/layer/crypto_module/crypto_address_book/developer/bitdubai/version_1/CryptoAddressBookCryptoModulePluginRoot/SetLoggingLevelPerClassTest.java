package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.CryptoAddressBookCryptoModulePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.CryptoAddressBookCryptoModulePluginRoot;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by natalia on 08/09/15.
 */
public class SetLoggingLevelPerClassTest {
    static final LogLevel logLevel = LogLevel.AGGRESSIVE_LOGGING;
    static final String classToTest = "Prueba";
    @Test
    public void test(){
        CryptoAddressBookCryptoModulePluginRoot root = new CryptoAddressBookCryptoModulePluginRoot();
        Map<String, LogLevel> c = new HashMap<String, LogLevel>() ;
        c.put(classToTest, logLevel);
        /**
         * I save the Log level for class Test
         */
        root.setLoggingLevelPerClass(c);

        /**
         * I retrieved the LogLevel for class Test.
         */
        assertEquals(CryptoAddressBookCryptoModulePluginRoot.getLogLevelByClass(classToTest), logLevel );

        LogLevel newLogLevel = LogLevel.MINIMAL_LOGGING;
        c.clear();
        c.put(classToTest, newLogLevel);
        root.setLoggingLevelPerClass(c);

        /**
         * I retrieved the LogLevel for class Test.
         */
        assertEquals(CryptoAddressBookCryptoModulePluginRoot.getLogLevelByClass(classToTest), newLogLevel);
    }

}

