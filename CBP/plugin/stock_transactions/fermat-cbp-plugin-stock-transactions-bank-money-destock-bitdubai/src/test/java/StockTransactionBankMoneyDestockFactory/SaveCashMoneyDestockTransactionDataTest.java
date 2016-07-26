package StockTransactionBankMoneyDestockFactory;


import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BankMoneyTransaction;
import com.bitdubai.fermat_cbp_plugin.layer.stock_transactions.bank_money_destock.developer.bitdubai.version_1.structure.StockTransactionBankMoneyDestockFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SaveCashMoneyDestockTransactionDataTest {

    @Test
    public void saveCashMoneyDestockTransactionData() throws Exception {
        StockTransactionBankMoneyDestockFactory stockTransactionBankMoneyDestockFactory = mock(StockTransactionBankMoneyDestockFactory.class, Mockito.RETURNS_DEEP_STUBS);
        doCallRealMethod().when(stockTransactionBankMoneyDestockFactory).saveBankMoneyDestockTransactionData(Mockito.any(BankMoneyTransaction.class));
    }

}
