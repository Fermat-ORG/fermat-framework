package BusinessTransactionBankMoneyRestockMonitorAgent;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_restock.developer.bitdubai.version_1.structure.events.BusinessTransactionBankMoneyRestockMonitorAgent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by José Vilchez on 25/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {

    @Test
    public void start() throws CantStartAgentException {
        BusinessTransactionBankMoneyRestockMonitorAgent businessTransactionBankMoneyRestockMonitorAgent = mock(BusinessTransactionBankMoneyRestockMonitorAgent.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(businessTransactionBankMoneyRestockMonitorAgent).start();
    }
}
