package unit.UnholdCryptoMoneyTransactionPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.UnHoldCryptoMoneyTransactionPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class StopTest {

    @Test
    public void stop() throws CantStartPluginException {
        UnHoldCryptoMoneyTransactionPluginRoot holdCryptoMoneyTransactionManager = mock(UnHoldCryptoMoneyTransactionPluginRoot.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(holdCryptoMoneyTransactionManager).stop();
    }

}
