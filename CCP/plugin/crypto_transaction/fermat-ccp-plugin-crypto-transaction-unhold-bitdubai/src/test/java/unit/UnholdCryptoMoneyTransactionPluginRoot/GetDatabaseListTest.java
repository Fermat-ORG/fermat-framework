package unit.UnholdCryptoMoneyTransactionPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.UnHoldCryptoMoneyTransactionPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 21/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest {

    @Test
    public void getDatabaseList() throws Exception{
        UnHoldCryptoMoneyTransactionPluginRoot unHoldCryptoMoneyTransactionPluginRoot = mock(UnHoldCryptoMoneyTransactionPluginRoot.class);
        when(unHoldCryptoMoneyTransactionPluginRoot.getDatabaseList(Mockito.any(DeveloperObjectFactory.class))).thenReturn(new ArrayList<DeveloperDatabase>());
        assertThat(unHoldCryptoMoneyTransactionPluginRoot.getDatabaseList(Mockito.any(DeveloperObjectFactory.class))).isNotNull();
    }

}
