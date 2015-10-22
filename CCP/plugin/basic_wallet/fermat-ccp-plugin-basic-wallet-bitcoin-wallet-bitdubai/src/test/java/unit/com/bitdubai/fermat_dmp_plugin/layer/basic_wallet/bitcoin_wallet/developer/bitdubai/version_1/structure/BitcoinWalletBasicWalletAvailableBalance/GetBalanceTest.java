package unit.com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWalletAvailableBalance;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWalletAvailableBalance;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletDatabaseConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;

/**
 * Created by jorgegonzalez on 2015.07.14..
 */
@RunWith(MockitoJUnitRunner.class)
public class GetBalanceTest {

    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTable mockTable;
    @Mock
    private List<DatabaseTableRecord> mockRecords;
    @Mock
    private DatabaseTableRecord mockRecord;

    private long mockAvailableBalance = 1L;

    private BitcoinWalletBasicWalletAvailableBalance testBalance;

    @Before
    public void setUpMockitoRules(){
        when(mockDatabase.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(mockRecords);
        when(mockRecords.get(0)).thenReturn(mockRecord);
        when(mockRecord.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME)).thenReturn(mockAvailableBalance);
    }

    @Before
    public void setUpAvailableBalance(){
        testBalance = new BitcoinWalletBasicWalletAvailableBalance(mockDatabase);
    }

    @Test
    public void GetBalance_SuccesfullyInvoked_ReturnsAvailableBalance() throws Exception{
        long checkBalance = testBalance.getBalance();
        assertThat(checkBalance).isEqualTo(mockAvailableBalance);
    }

    @Test
    public void GetBalance_OpenDatabaseCantOpenDatabase_ThrowsCantCalculateBalanceException() throws Exception{
        doThrow(new CantOpenDatabaseException("MOCK", null, null, null)).when(mockDatabase).openDatabase();

        catchException(testBalance).getBalance();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCalculateBalanceException.class);
    }

    @Test
    public void GetBalance_OpenDatabaseDatabaseNotFound_ThrowsCantCalculateBalanceException() throws Exception{
        doThrow(new DatabaseNotFoundException("MOCK", null, null, null)).when(mockDatabase).openDatabase();

        catchException(testBalance).getBalance();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCalculateBalanceException.class);
    }

    @Test
    public void GetBalance_DaoCantCalculateBalanceException_ThrowsCantCalculateBalanceException() throws Exception{
        doThrow(new CantLoadTableToMemoryException("MOCK", null, null, null)).when(mockTable).loadToMemory();

        catchException(testBalance).getBalance();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCalculateBalanceException.class);
    }

    @Test
    public void GetBalance_GeneralException_ReturnsAvailableBalance() throws Exception{
        when(mockDatabase.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME)).thenReturn(null);

        catchException(testBalance).getBalance();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCalculateBalanceException.class);
    }

}
