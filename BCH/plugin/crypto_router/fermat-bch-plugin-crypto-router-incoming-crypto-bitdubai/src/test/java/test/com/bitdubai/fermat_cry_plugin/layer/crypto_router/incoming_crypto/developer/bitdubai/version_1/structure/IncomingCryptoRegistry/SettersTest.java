package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by franklin on 06/08/15.
 */
public class SettersTest {
    @Test
    public void testSetErrorManager(){
        IncomingCryptoTransactionPluginRoot root  = new IncomingCryptoTransactionPluginRoot();
        ErrorManager errorManager = mock(ErrorManager.class);
        root.setErrorManager(errorManager);
    }

    @Test
    public void testSetLogManager(){
        IncomingCryptoTransactionPluginRoot root  = new IncomingCryptoTransactionPluginRoot();
        LogManager logManager= mock(LogManager.class);
        root.setLogManager(logManager);
    }
}
