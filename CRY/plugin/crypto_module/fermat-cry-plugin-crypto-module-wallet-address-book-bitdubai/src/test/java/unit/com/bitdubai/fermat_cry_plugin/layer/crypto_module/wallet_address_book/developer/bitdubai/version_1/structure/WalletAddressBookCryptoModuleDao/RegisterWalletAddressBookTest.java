package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleDao;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantRegisterWalletAddressBookException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleDao;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class RegisterWalletAddressBookTest extends TestCase {

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    DatabaseTable databaseTable;

    @Mock
    DatabaseTableRecord databaseTableRecord;

    String walletPublicKey;

    ReferenceWallet referenceWallet;

    CryptoAddress cryptoAddress;


    WalletAddressBookCryptoModuleDao dao;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {
        walletPublicKey = new ECCKeyPair().getPublicKey();
        referenceWallet = ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;
        cryptoAddress = new CryptoAddress("asdadas", CryptoCurrency.BITCOIN);
        pluginId = UUID.randomUUID();
        dao = new WalletAddressBookCryptoModuleDao(pluginDatabaseSystem, pluginId);
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(database);
        dao.initialize();

    }

    @Test
    public void testRegister_NotNull() throws Exception {
        when(database.getTable(anyString())).thenReturn(databaseTable);
        when(databaseTable.getEmptyRecord()).thenReturn(databaseTableRecord);
        dao.registerWalletAddressBookModule(cryptoAddress, referenceWallet, walletPublicKey);
    }

    @Test(expected=CantRegisterWalletAddressBookException.class)
    public void testCreateDatabase_CantInsertRecordException_CantRegisterWalletAddressBookException() throws Exception {
        when(database.getTable(anyString())).thenReturn(databaseTable);
        when(databaseTable.getEmptyRecord()).thenReturn(databaseTableRecord);
        doThrow(new CantInsertRecordException()).when(databaseTable).insertRecord(any(DatabaseTableRecord.class));

        dao.registerWalletAddressBookModule(cryptoAddress, referenceWallet, walletPublicKey);
    }

    @Test(expected=CantRegisterWalletAddressBookException.class)
    public void testRegister_walletPublicKeyNull_CantRegisterWalletAddressBookException() throws Exception {
        dao.registerWalletAddressBookModule(cryptoAddress, referenceWallet, null);
    }

    @Test(expected=CantRegisterWalletAddressBookException.class)
    public void testRegister_walletTypeNull_CantRegisterWalletAddressBookException() throws Exception {
        dao.registerWalletAddressBookModule(cryptoAddress, null, walletPublicKey);
    }

    @Test(expected=CantRegisterWalletAddressBookException.class)
    public void testRegister_cryptoAddressNull_CantRegisterWalletAddressBookException() throws Exception {
        dao.registerWalletAddressBookModule(null, referenceWallet, walletPublicKey);
    }
}
