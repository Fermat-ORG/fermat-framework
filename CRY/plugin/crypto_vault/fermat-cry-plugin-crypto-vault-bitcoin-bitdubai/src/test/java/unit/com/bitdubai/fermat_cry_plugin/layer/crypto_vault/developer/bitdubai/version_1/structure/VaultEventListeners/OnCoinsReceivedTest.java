package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.VaultEventListeners;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.VaultEventListeners;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by rodrigo on 2015.07.16..
 */
@RunWith(MockitoJUnitRunner.class)
public class OnCoinsReceivedTest {
    @Mock
    EventManager eventManager;

    @Mock
    LogManager logManager;

    @Mock
    ErrorManager errorManager;

    @Mock
    Database database;

    @Mock
    Wallet wallet;

    @Mock
    Transaction tx;

    @Mock
    DatabaseTable table;

    @Mock
    DatabaseTableRecord record;

    @Mock
    DatabaseTransaction transaction;


    @Test
    public void test(){
        List<DatabaseTableRecord> records = mock(ArrayList.class);
        when(database.getTable(anyString())).thenReturn(table);
        when(database.newTransaction()).thenReturn(transaction);
        when(table.getRecords()).thenReturn(records);
        when(table.getEmptyRecord()).thenReturn(record);
        when(records.isEmpty()).thenReturn(true);
        VaultEventListeners eventListeners = new VaultEventListeners(database,errorManager, eventManager, logManager);
        eventListeners.onCoinsReceived(wallet, tx, Coin.CENT, Coin.CENT);
    }


}
