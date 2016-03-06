package unit.HoldCryptoMoneyTransactionPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.HoldCryptoMoneyTransactionPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions.MissingHoldCryptoDataException;

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
public class StartTest {

    @Test
    public void start() throws DatabaseOperationException, MissingHoldCryptoDataException, CantStartPluginException {
        HoldCryptoMoneyTransactionPluginRoot holdCryptoMoneyTransactionManager = mock(HoldCryptoMoneyTransactionPluginRoot.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(holdCryptoMoneyTransactionManager).start();
    }

}
