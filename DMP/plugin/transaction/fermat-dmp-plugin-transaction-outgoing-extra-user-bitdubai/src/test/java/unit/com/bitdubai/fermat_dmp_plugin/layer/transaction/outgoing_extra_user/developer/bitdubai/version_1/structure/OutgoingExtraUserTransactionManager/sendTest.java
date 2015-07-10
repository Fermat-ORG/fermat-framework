package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserTransactionManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.CantSendFundsException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.OutgoingExtraUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserTransactionManager;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 10/07/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class sendTest {

    @Mock
    private BitcoinWalletManager mockBitcoinWalletManager;

    @Mock
    private BitcoinWalletWallet mockBitcoinWalletWallet;

    @Mock
    private BitcoinWalletBalance mockBitcoinWalletBalance;

    @Mock
    private CryptoVaultManager mockCryptoVaultManager;
    @Mock
    private ErrorManager mockErrorManager;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private EventManager mockEventManager;

    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTableFactory mockTableFactory;
    @Mock
    private Database mockDatabase;

    private CryptoAddress cryptoAddress;
    private UUID testId;
    private UUID testwalletID;
    private String testDataBaseName;
    private OutgoingExtraUserTransactionManager testTransactionManager;


    public void setUpTestValues(){
        cryptoAddress = new CryptoAddress();
        cryptoAddress.setCryptoCurrency(CryptoCurrency.BITCOIN);
        cryptoAddress.setAddress(UUID.randomUUID().toString());
        testwalletID = UUID.randomUUID();
        testId = UUID.randomUUID();
        testDataBaseName = OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME;


    }

    public void setUpGeneralMockitoRules() throws Exception{

        when(mockPluginDatabaseSystem.createDatabase(testId, testDataBaseName)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
        when(mockBitcoinWalletManager.loadWallet(any(UUID.class))).thenReturn(mockBitcoinWalletWallet);
        when(mockBitcoinWalletWallet.getAvailableBalance()).thenReturn(mockBitcoinWalletBalance);
    }

    @Before
    public void setUp() throws Exception{
        setUpTestValues();
        setUpGeneralMockitoRules();
    }

    @Test
    public void Send_ThrowsInsufficientFundsException_InsufficientFundsException() throws Exception {
        testTransactionManager = new OutgoingExtraUserTransactionManager();
        testTransactionManager.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testTransactionManager.setPluginId(testId);
        testTransactionManager.setErrorManager(mockErrorManager);



        testTransactionManager.setBitcoinWalletManager(mockBitcoinWalletManager);
        testTransactionManager.setCryptoVaultManager(mockCryptoVaultManager);

        catchException(testTransactionManager).send(testwalletID, cryptoAddress, 20, "test");

        assertThat(caughtException()).isInstanceOf(InsufficientFundsException.class);


    }

    @Test
    public void Send_ThrowsInsufficientFundsException_CantSendFundsException() throws Exception {
        testTransactionManager = new OutgoingExtraUserTransactionManager();
        testTransactionManager.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testTransactionManager.setPluginId(testId);
        testTransactionManager.setErrorManager(mockErrorManager);



        testTransactionManager.setBitcoinWalletManager(mockBitcoinWalletManager);
        testTransactionManager.setCryptoVaultManager(mockCryptoVaultManager);

        catchException(testTransactionManager).send(testwalletID, cryptoAddress, 20, "test");

        assertThat(caughtException()).isInstanceOf(CantInsertRecordException.class);

    }

}


