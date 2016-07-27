package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRootTest;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRoot;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 28/01/16.
 */
public class testSetLoggingLevelPerClass {
    private CustomerOfflinePaymentPluginRoot customerOfflinePaymentPluginRoot = new CustomerOfflinePaymentPluginRoot();
    static final LogLevel logLevel = LogLevel.AGGRESSIVE_LOGGING;
    static final String classToTest = "Test";

    @Test
    public void TestSetLogLevelPerClass() {
        Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
        newLoggingLevel.put(classToTest, logLevel);
        customerOfflinePaymentPluginRoot.setLoggingLevelPerClass(newLoggingLevel);
        assertEquals(CustomerOfflinePaymentPluginRoot.getLogLevelByClass(classToTest), logLevel);
        LogLevel newLogLevel = LogLevel.MINIMAL_LOGGING;
        newLoggingLevel.clear();
        newLoggingLevel.put(classToTest, newLogLevel);
        customerOfflinePaymentPluginRoot.setLoggingLevelPerClass(newLoggingLevel);
        assertEquals(CustomerOfflinePaymentPluginRoot.getLogLevelByClass(classToTest), newLogLevel);
    }

    @Test(expected = Exception.class)
    public void TestSetLogLevelPerClass_Should_Return_Exception() throws Exception {
        Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
        newLoggingLevel.put(classToTest, logLevel);
        customerOfflinePaymentPluginRoot.setLoggingLevelPerClass(null);
    }

    @Test
    public void TestGetLogLevelPerClass_Should_Return_Default_Log_Level() throws Exception {
        Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();
        newLoggingLevel.put(classToTest, logLevel);
        customerOfflinePaymentPluginRoot.setLoggingLevelPerClass(newLoggingLevel);
        assertEquals(customerOfflinePaymentPluginRoot.getLogLevelByClass(null), logLevel.MINIMAL_LOGGING);
    }


}
