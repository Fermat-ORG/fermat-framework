package unit.com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWalletDaoTransaction;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseTransactionFailedException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.exceptions.CantExecuteBitconTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWalletDaoTransaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;
/**
 * Created by jorgegonzalez on 2015.07.13..
 */
@RunWith(MockitoJUnitRunner.class)
public class ExecuteTransactionTest {

    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTransaction mockTransaction;

    @Mock
    private DatabaseTable mockTable;
    @Mock
    private DatabaseTableRecord mockInsertRecord;
    @Mock
    private DatabaseTableRecord mockUpdateRecord;

    private BitcoinWalletBasicWalletDaoTransaction testDaoTransaction;

    @Before
    public void setUpTransaction(){
        when(mockDatabase.newTransaction()).thenReturn(mockTransaction);
        testDaoTransaction = new BitcoinWalletBasicWalletDaoTransaction(mockDatabase);
    }

    @Test
    public void ExecuteTransaction_OperationsExecutedSuccessFull_NoExceptions() throws Exception{
        catchException(testDaoTransaction).executeTransaction(mockTable, mockInsertRecord, mockTable, mockUpdateRecord);
        assertThat(caughtException()).isNull();
    }

    @Test
    public void ExecuteTransaction_DatabaseTransactionFailedException_ThrowsCantExecuteBitconTransactionException() throws Exception{
        doThrow(new DatabaseTransactionFailedException("MOCK", null,null,null)).when(mockDatabase).executeTransaction(mockTransaction);
        catchException(testDaoTransaction).executeTransaction(mockTable, mockInsertRecord, mockTable, mockUpdateRecord);
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantExecuteBitconTransactionException.class);
    }

    @Test
    public void ExecuteTransaction_GeneralException_ThrowsCantExecuteBitconTransactionException() throws Exception{
        when(mockDatabase.newTransaction()).thenReturn(null);
        catchException(testDaoTransaction).executeTransaction(mockTable, mockInsertRecord, mockTable, mockUpdateRecord);
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantExecuteBitconTransactionException.class);
    }
}
