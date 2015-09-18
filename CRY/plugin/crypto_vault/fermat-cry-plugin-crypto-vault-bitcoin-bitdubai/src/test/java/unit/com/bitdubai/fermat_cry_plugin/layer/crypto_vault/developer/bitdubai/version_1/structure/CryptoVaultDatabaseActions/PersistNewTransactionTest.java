package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 27/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class PersistNewTransactionTest {
    @Mock
    Database mockDatabase;

    @Mock
    DatabaseTransaction mockTransaction;

    @Mock
    DatabaseTable mockTable;

    @Mock
    DatabaseTableRecord mockRecord;

    @Mock
    EventManager mockEventManager;

    @Mock
    ErrorManager mockErrorManager;


    CryptoVaultDatabaseActions cryptoVaultDatabaseActions;

    @Before
    public void setUp() throws Exception{

        cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(mockDatabase,mockErrorManager, mockEventManager);


        when(mockDatabase.newTransaction()).thenReturn(mockTransaction);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockRecord);



    }
    @Test
    public void persistNewTransaction_persistOk_Test() throws CantExecuteQueryException {
        cryptoVaultDatabaseActions.persistNewTransaction("1234", "abcdef");
    }

    @Test
    public void persistNewTransactionTest_persistError_ThrowsCantExecuteQueryException() throws Exception {
        when(mockDatabase.newTransaction()).thenReturn(null);

        catchException(cryptoVaultDatabaseActions).persistNewTransaction("1234", "abcdef");
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantExecuteQueryException.class);
    }

}
