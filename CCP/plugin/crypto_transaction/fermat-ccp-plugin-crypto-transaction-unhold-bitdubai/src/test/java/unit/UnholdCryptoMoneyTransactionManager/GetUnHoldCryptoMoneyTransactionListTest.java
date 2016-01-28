package unit.UnholdCryptoMoneyTransactionManager;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.Unhold.interfaces.CryptoUnholdTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.structure.UnHoldCryptoMoneyTransactionManager;

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
public class GetUnHoldCryptoMoneyTransactionListTest {

    @Test
    public void getUnHoldCryptoMoneyTransactionList() throws Exception{
        UnHoldCryptoMoneyTransactionManager unHoldCryptoMoneyTransactionManager = mock(UnHoldCryptoMoneyTransactionManager.class);
        when(unHoldCryptoMoneyTransactionManager.getUnHoldCryptoMoneyTransactionList(Mockito.any(DatabaseTableFilter.class))).thenReturn(new ArrayList<CryptoUnholdTransaction>());
        assertThat(unHoldCryptoMoneyTransactionManager.getUnHoldCryptoMoneyTransactionList(Mockito.any(DatabaseTableFilter.class))).isNotNull();
    }

}
