package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class getCurrentTransactionProtocolStatusTest {
    @Mock
    Database database;

    @Mock
    DatabaseTransaction transaction;

    @Mock
    DatabaseTable table;

    @Mock
    DatabaseTableRecord record;

    @Mock
    EventManager eventManager;

    @Mock
    ErrorManager errorManager;

    @Test
    public void getCurrentTransactionProtocolStatus_getCurrentStatus() throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        List<DatabaseTableRecord> records = mock(ArrayList.class);
        records.add(record);

        when(database.getTable(anyString())).thenReturn(table);
        when(table.getRecords()).thenReturn(records);
        when(records.get(0)).thenReturn(record);
        when(record.getStringValue(anyString())).thenReturn(ProtocolStatus.TO_BE_NOTIFIED.toString());
        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);

        cryptoVaultDatabaseActions.getCurrentTransactionProtocolStatus(UUID.randomUUID());
    }

    @Ignore
    @Test (expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getCurrentTransactionProtocolStatus_UnexpectedResultReturnedFromDatabaseException() throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        List<DatabaseTableRecord> records = mock(ArrayList.class);


        when(database.getTable(anyString())).thenReturn(table);
        when(table.getRecords()).thenReturn(records);
        when(records.size()).thenReturn(2);
        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);

        cryptoVaultDatabaseActions.getCurrentTransactionProtocolStatus(UUID.randomUUID());
    }

    @Test (expected = CantExecuteQueryException.class)
    public void getCurrentTransactionProtocolStatus_CantExecuteQueryException_test() throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException, CantLoadTableToMemoryException {
        when(database.getTable(anyString())).thenReturn(table);
        doThrow(new CantLoadTableToMemoryException()).when(table).loadToMemory();
        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database, errorManager, eventManager);

        cryptoVaultDatabaseActions.getCurrentTransactionProtocolStatus(UUID.randomUUID());
    }
}
