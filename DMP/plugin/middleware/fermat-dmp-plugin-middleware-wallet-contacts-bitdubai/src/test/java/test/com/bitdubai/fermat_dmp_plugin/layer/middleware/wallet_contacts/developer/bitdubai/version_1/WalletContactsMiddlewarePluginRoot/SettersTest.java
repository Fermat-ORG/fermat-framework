package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by Nerio on 18/07/15.
 */
public class SettersTest {
    @Test
    public void testSetErrorManager(){
        WalletContactsMiddlewarePluginRoot root  = new WalletContactsMiddlewarePluginRoot();
        ErrorManager errorManager = mock(ErrorManager.class);
        root.setErrorManager(errorManager);
    }

    @Test
    public void testSetLogManager(){
        WalletContactsMiddlewarePluginRoot root  = new WalletContactsMiddlewarePluginRoot();
        LogManager logManager= mock(LogManager.class);
        root.setLogManager(logManager);
    }
}
