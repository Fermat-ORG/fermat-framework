package unit.HoldCryptoMoneyTransactionPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.HoldCryptoMoneyTransactionPluginRoot;

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
        HoldCryptoMoneyTransactionPluginRoot holdCryptoMoneyTransactionManager = mock(HoldCryptoMoneyTransactionPluginRoot.class);
        when(holdCryptoMoneyTransactionManager.getDatabaseList(Mockito.any(DeveloperObjectFactory.class))).thenReturn(new ArrayList<DeveloperDatabase>());
        assertThat(holdCryptoMoneyTransactionManager.getDatabaseList(Mockito.any(DeveloperObjectFactory.class))).isNotNull();
    }

}
