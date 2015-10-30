package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.Address;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginFileSystem;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 27/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetPendingTransactionsTest {


    @Mock
    Database database;

    @Mock
    EventManager eventManager;

    @Mock
    ErrorManager errorManager;

    @Mock
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;

    @Mock
    LogManager logManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    private List<DatabaseTableRecord> mockRecords;

    @Mock
    private DatabaseTableRecord mockRecord;

    @Mock
    private LogManager mockLogManager;

    @Mock
    private DeviceUserManager mockDeviceUserManager;

    @Mock
    private Address mockAddress;

    @Mock
    DeviceUser mockDeviceUser;

    @Mock
    private Database mockDatabase;

    @Mock
    private DatabaseTable mockTable;

    @Mock
    private CryptoAddress mockCryptoAddress;

    @Mock
    CryptoVaultDatabaseActions mockCryptoVaultDatabaseActions;

    private UUID pluginId = UUID.randomUUID();

    private BitcoinCryptoVault bitcoinCryptoVault;

    private String userPublicKey = "replace_user_public_key";

    @Before
    public void setUp() throws Exception{

        bitcoinCryptoVault = new BitcoinCryptoVault(userPublicKey);
        pluginFileSystem = new MockedPluginFileSystem();
        bitcoinCryptoVault.setErrorManager(errorManager);
        bitcoinCryptoVault.setEventManager(eventManager);
        bitcoinCryptoVault.setLogManager(logManager);
        bitcoinCryptoVault.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        bitcoinCryptoVault.setPluginDatabaseSystem(pluginDatabaseSystem);
        bitcoinCryptoVault.setPluginFileSystem(pluginFileSystem);
        bitcoinCryptoVault.setUserPublicKey(userPublicKey);
        bitcoinCryptoVault.setDatabase(mockDatabase);

        when(pluginDatabaseSystem.openDatabase(pluginId, userPublicKey)).thenReturn(mockDatabase);

        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);



    }

    @Test
    public void getPendingTransactionsTest_GetOk_ThrowsCantDeliverPendingTransactionsException() throws Exception {

        //TODO: hay que mockear un HashMap<String, String> con trasacciones para que procese
         List<com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction> transactionList=  bitcoinCryptoVault.getPendingTransactions(Specialist.DEVICE_USER_SPECIALIST);

        Assertions.assertThat(transactionList)
                .isNotNull();
    }



}
