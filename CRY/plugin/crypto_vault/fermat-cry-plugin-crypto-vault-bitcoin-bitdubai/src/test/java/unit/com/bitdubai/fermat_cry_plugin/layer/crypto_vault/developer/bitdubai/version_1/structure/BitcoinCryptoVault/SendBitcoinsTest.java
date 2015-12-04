package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

/**
 * Created by rodrigo on 2015.07.15..
 */
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantCreateCryptoWalletException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CryptoTransactionAlreadySentException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientCryptoFundsException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SendBitcoinsTest {

    final String userPublicKey =  "replace_device_user_key";
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
    PluginTextFile pluginTextFile;

    @Mock DatabaseTable table;

    @Mock DatabaseTableRecord record;

    private UUID pluginId = UUID.randomUUID();

    @Test (expected = CouldNotSendMoneyException.class)
    public void sendBitcoins_RaiseInsufficientFundsException() throws CantCreateCryptoWalletException, InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException, CantCreateFileException {
       BitcoinCryptoVault vault = new BitcoinCryptoVault(userPublicKey);
        List<DatabaseTableRecord> records = mock(ArrayList.class);

        when(pluginFileSystem.createTextFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(pluginTextFile);
        when(database.getTable(anyString())).thenReturn(table);
        when(table.getRecords()).thenReturn(records);
        when(records.isEmpty()).thenReturn(true);

        vault.setDatabase(database);
        vault.setErrorManager(errorManager);
        vault.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        vault.setUserPublicKey(userPublicKey);
        vault.setLogManager(logManager);
        vault.setPluginId(pluginId);
        vault.setEventManager(eventManager);
        vault.setPluginDatabaseSystem(pluginDatabaseSystem);
        vault.setPluginFileSystem(pluginFileSystem);



        vault.sendBitcoins(UUID.randomUUID(), new CryptoAddress("mwTdg897T6WEFRnFVm87APwpUeQb6jMgi6", CryptoCurrency.BITCOIN), 10000);
    }

    @Test (expected = InvalidSendToAddressException.class)
    public void sendBitcoins_InvalidAddressException() throws CantCreateCryptoWalletException, InsufficientCryptoFundsException, InvalidSendToAddressException, CouldNotSendMoneyException, CryptoTransactionAlreadySentException, CantCreateFileException {
        BitcoinCryptoVault vault = new BitcoinCryptoVault(userPublicKey);
        List<DatabaseTableRecord> records = mock(ArrayList.class);

        when(pluginFileSystem.createTextFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(pluginTextFile);
        when(database.getTable(anyString())).thenReturn(table);
        when(table.getRecords()).thenReturn(records);
        when(records.isEmpty()).thenReturn(true);

        vault.setDatabase(database);
        vault.setErrorManager(errorManager);
        vault.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        vault.setUserPublicKey(userPublicKey);
        vault.setLogManager(logManager);
        vault.setPluginId(UUID.randomUUID());
        vault.setEventManager(eventManager);
        vault.setPluginDatabaseSystem(pluginDatabaseSystem);
        vault.setPluginFileSystem(pluginFileSystem);

        vault.sendBitcoins(UUID.randomUUID(), new CryptoAddress("badAddress", CryptoCurrency.BITCOIN), 10000);
    }
}
