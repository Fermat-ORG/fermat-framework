package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import org.bitcoinj.core.Wallet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 27/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetCryptoStatusTest {
    @Mock
    Wallet mockVault;

    @Mock
    CryptoAddress mockCryptoAddress;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    PluginTextFile mockPluginTextFile;

    @Mock
    private LogManager mockLogManager;

    @Mock
    private Database mockDatabase;

    @Mock
    private DatabaseTable mockTable;

    @Mock
    private DatabaseTransaction mockDatabaseTransaction;

    @Mock
    private List<DatabaseTableRecord> mockRecords;
    @Mock
    private DatabaseTableRecord mockRecord;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    private BitcoinCryptoVault bitcoinCryptoVault;

    private UUID pluginId = UUID.randomUUID();

    private String userPublicKey = "replace_user_public_key";

    @Before
    public void setUp() throws Exception{

        bitcoinCryptoVault = new BitcoinCryptoVault(userPublicKey);
        bitcoinCryptoVault.setPluginFileSystem(pluginFileSystem);
        bitcoinCryptoVault.setPluginDatabaseSystem(pluginDatabaseSystem);
        bitcoinCryptoVault.setPluginFileSystem(pluginFileSystem);
        bitcoinCryptoVault.setUserPublicKey(userPublicKey);
        bitcoinCryptoVault.setDatabase(mockDatabase);

        bitcoinCryptoVault.setPluginId(pluginId);
        bitcoinCryptoVault.setLogManager(mockLogManager);
        when(pluginFileSystem.createTextFile(pluginId, userPublicKey, userPublicKey.toString() + ".vault", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(mockPluginTextFile);

        bitcoinCryptoVault.loadOrCreateVault();

        when(pluginDatabaseSystem.openDatabase(pluginId, userPublicKey)).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        when(mockDatabase.newTransaction()).thenReturn(mockDatabaseTransaction);
        when(mockTable.getRecords()).thenReturn(mockRecords);
        Mockito.doReturn(mockRecord).when(mockRecords).get(0);


    }
    @Test
    public void getCryptoStatusTest_GetOk_ThrowsCantExecuteQueryException() throws Exception {
    //TODO: el select no retorna datos por lo que el metodo retorna una exception
        catchException(bitcoinCryptoVault).getCryptoStatus(UUID.randomUUID());
        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantExecuteQueryException.class);

    }

    public void getCryptoStatusTest_GetError_ThrowsCantExecuteQueryException() throws Exception {
        Mockito.doReturn(null).when(mockRecords).get(0);
        catchException(bitcoinCryptoVault).getCryptoStatus(UUID.randomUUID());
        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantExecuteQueryException.class);

    }

}
