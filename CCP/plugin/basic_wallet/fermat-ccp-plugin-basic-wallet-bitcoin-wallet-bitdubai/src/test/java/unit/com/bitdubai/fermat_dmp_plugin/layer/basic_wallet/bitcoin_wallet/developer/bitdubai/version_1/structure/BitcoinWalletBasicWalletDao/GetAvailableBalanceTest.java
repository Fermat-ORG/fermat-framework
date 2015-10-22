package unit.com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWalletDao;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWalletDao;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletDatabaseConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by jorgegonzalez on 2015.07.13..
 */
@RunWith(MockitoJUnitRunner.class)
public class GetAvailableBalanceTest {

    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTable mockTable;
    @Mock
    private List<DatabaseTableRecord> mockRecords;
    @Mock
    private DatabaseTableRecord mockRecord;

    private long mockAvailableBalance = 1L;

    private BitcoinWalletBasicWalletDao testWalletDao;

    @Before
    public void setUpMockitoRules(){
        when(mockDatabase.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(mockRecords);
        when(mockRecords.get(0)).thenReturn(mockRecord);
        when(mockRecord.getLongValue(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_AVAILABLE_BALANCE_COLUMN_NAME)).thenReturn(mockAvailableBalance);
    }

    @Before
    public void setUpWalletDao(){
        testWalletDao = new BitcoinWalletBasicWalletDao(mockDatabase);
    }

    @Test
    public void GetAvailableBalance_InvokedSuccessFully_ReturnsAvailableBalance() throws Exception{
        long testAvailableBalance = testWalletDao.getAvailableBalance();
        assertThat(testAvailableBalance).isEqualTo(mockAvailableBalance);
    }

    @Test
    public void GetAvailableBalance_OpenDatabaseCantOpenDatabaseException_ThrowsCantCalculateBalanceException() throws Exception{
        doThrow(new CantOpenDatabaseException("MOCK", null, null, null)).when(mockDatabase).openDatabase();
        catchException(testWalletDao).getAvailableBalance();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCalculateBalanceException.class);
    }

    @Test
    public void GetAvailableBalance_OpenDatabaseDatabaseNotFoundException_ThrowsCantCalculateBalanceException() throws Exception{
        doThrow(new DatabaseNotFoundException("MOCK", null, null, null)).when(mockDatabase).openDatabase();
        catchException(testWalletDao).getAvailableBalance();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCalculateBalanceException.class);
    }

    @Test
    public void GetAvailableBalance_LoadTableCantLoadTableToMemoryException_ThrowsCantCalculateBalanceException() throws Exception{
        doThrow(new CantLoadTableToMemoryException("MOCK", null, null, null)).when(mockTable).loadToMemory();
        catchException(testWalletDao).getAvailableBalance();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCalculateBalanceException.class);
    }

    @Test
    public void GetAvailableBalance_GeneralException_ThrowsCantCalculateBalanceException() throws Exception{
        when(mockTable.getRecords()).thenReturn(null);
        catchException(testWalletDao).getAvailableBalance();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCalculateBalanceException.class);
    }
}
