/*
package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserTransactionPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import ErrorManager;
import EventManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.OutgoingExtraUserTransactionPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

*/
/**
 * Created by natalia on 10/07/15.
 *//*


@RunWith(MockitoJUnitRunner.class)
public class StartTest  {

    @Mock
    private BitcoinWalletManager mockBitcoinWalletManager;
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

    private UUID testId;
    private String testDataBaseName;
    private OutgoingExtraUserTransactionPluginRoot testPluginRoot;


    public void setUpTestValues(){
        testId = UUID.randomUUID();
        testDataBaseName = OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME;


    }

    public void setUpGeneralMockitoRules() throws Exception{
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockPluginDatabaseSystem.createDatabase(testId, testDataBaseName)).thenReturn(mockDatabase);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
    }

    @Before
    public void setUp() throws Exception{
        setUpTestValues();
        setUpGeneralMockitoRules();
    }

    @Test
    public void Start_SuccessFully_ThrowsCantStartPluginException() throws Exception {
         testPluginRoot = new OutgoingExtraUserTransactionPluginRoot();
       testPluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testPluginRoot.setEventManager(mockEventManager);
        testPluginRoot.setErrorManager(mockErrorManager);
        testPluginRoot.setBitcoinWalletManager(mockBitcoinWalletManager);
        testPluginRoot.setCryptoVaultManager(mockCryptoVaultManager);
        testPluginRoot.setId(testId);

        catchException(testPluginRoot).start();

        Assertions.assertThat(caughtException()).isNull();

        Assertions.assertThat(testPluginRoot.getStatus()).isEqualTo(ServiceStatus.STARTED);

    }

}
*/
