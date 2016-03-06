package unit.UnholdCryptoMoneyTransactionPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.UnHoldCryptoMoneyTransactionPluginRoot;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by José Vilchez on 21/01/16.
 */
public class GetDatabaseTableContentTest {

    @Test
    public void getDatabaseTableContent() throws Exception{
        UnHoldCryptoMoneyTransactionPluginRoot unHoldCryptoMoneyTransactionPluginRoot = mock(UnHoldCryptoMoneyTransactionPluginRoot.class);
        when(unHoldCryptoMoneyTransactionPluginRoot.getDatabaseTableContent(Mockito.any(DeveloperObjectFactory.class), Mockito.any(DeveloperDatabase.class), Mockito.any(DeveloperDatabaseTable.class))).thenReturn(new ArrayList<DeveloperDatabaseTableRecord>());
        assertThat(unHoldCryptoMoneyTransactionPluginRoot.getDatabaseTableContent(Mockito.any(DeveloperObjectFactory.class), Mockito.any(DeveloperDatabase.class), Mockito.any(DeveloperDatabaseTable.class))).isNotNull();
    }
}
