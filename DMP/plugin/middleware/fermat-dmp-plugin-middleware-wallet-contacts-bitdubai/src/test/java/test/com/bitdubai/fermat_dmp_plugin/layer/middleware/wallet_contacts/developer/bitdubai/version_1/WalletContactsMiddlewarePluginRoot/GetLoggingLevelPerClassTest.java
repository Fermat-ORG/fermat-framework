package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nerio on 18/07/15.
 */
public class GetLoggingLevelPerClassTest {

    static final String CLASS = "ClassToTest";
    static final LogLevel LOG_LEVEL = LogLevel.MODERATE_LOGGING;

    @Test
    public void testMethod(){
        WalletContactsMiddlewarePluginRoot root = new WalletContactsMiddlewarePluginRoot();
        Map<String, LogLevel> data = new HashMap<String, LogLevel>();
        data.put(CLASS, LOG_LEVEL);
        root.setLoggingLevelPerClass(data);

        assertEquals(WalletContactsMiddlewarePluginRoot.getLogLevelByClass(CLASS), LOG_LEVEL);

        data.clear();
        LogLevel newLogLevel = LogLevel.AGGRESSIVE_LOGGING;
        data.put(CLASS, newLogLevel);
        root.setLoggingLevelPerClass(data);

        assertEquals(WalletContactsMiddlewarePluginRoot.getLogLevelByClass(CLASS), newLogLevel);

    }
}