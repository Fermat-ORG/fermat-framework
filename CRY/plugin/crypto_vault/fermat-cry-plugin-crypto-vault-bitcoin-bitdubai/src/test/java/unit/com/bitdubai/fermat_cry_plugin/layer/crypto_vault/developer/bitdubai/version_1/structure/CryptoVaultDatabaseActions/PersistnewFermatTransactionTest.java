package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class PersistnewFermatTransactionTest {
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
    public void persistnewFermatTransaction_persist_Test() throws CantExecuteQueryException {
        when(database.newTransaction()).thenReturn(transaction);
        when(database.getTable(anyString())).thenReturn(table);
        when(table.getEmptyRecord()).thenReturn(record);

        CryptoVaultDatabaseActions cryptoVaultDatabaseActions = new CryptoVaultDatabaseActions(database,errorManager, eventManager);
        cryptoVaultDatabaseActions.persistnewFermatTransaction("");
    }

}
