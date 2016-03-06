package unit.HoldCryptoMoneyTransactionManager;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransaction;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.structure.HoldCryptoMoneyTransactionManager;

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
public class GetHoldCryptoMoneyTransactionListTest {

    @Test
    public void getHoldCryptoMoneyTransactionList() throws Exception{
        HoldCryptoMoneyTransactionManager holdCryptoMoneyTransactionManager = mock(HoldCryptoMoneyTransactionManager.class);
        when(holdCryptoMoneyTransactionManager.getHoldCryptoMoneyTransactionList(Mockito.any(DatabaseTableFilter.class))).thenReturn(new ArrayList<CryptoHoldTransaction>());
        assertThat(holdCryptoMoneyTransactionManager.getHoldCryptoMoneyTransactionList(Mockito.any(DatabaseTableFilter.class))).isNotNull();
    }

}
