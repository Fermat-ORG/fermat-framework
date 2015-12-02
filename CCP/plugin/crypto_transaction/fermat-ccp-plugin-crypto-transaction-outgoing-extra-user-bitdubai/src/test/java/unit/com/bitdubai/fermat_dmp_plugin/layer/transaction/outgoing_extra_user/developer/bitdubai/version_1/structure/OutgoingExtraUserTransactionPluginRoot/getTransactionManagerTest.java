/*
package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserTransactionPluginRoot;

*/
/**
 * Created by natalia on 10/07/15.
 *//*


import org.junit.Before;
import org.mockito.Mock;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import ErrorManager;
import EventManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.PlatformCryptoVaultManager;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.OutgoingExtraUserTransactionPluginRoot;
import com.googlecode.catchexception.CatchException;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Matchers.any;


public class getTransactionManagerTest {

    @Mock
    private BitcoinWalletManager mockBitcoinWalletManager;
    @Mock
    private PlatformCryptoVaultManager mockCryptoVaultManager;
    @Mock
    private ErrorManager mockErrorManager;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private EventManager mockEventManager;

    private UUID testId;

    @Before
    public void setUp() throws Exception{
        testId = UUID.randomUUID();
    }

    @Test
    public void getTransactionManager_SuccessFully(){
        OutgoingExtraUserTransactionPluginRoot testPluginRoot = new OutgoingExtraUserTransactionPluginRoot();
        testPluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testPluginRoot.setEventManager(mockEventManager);
        testPluginRoot.setErrorManager(mockErrorManager);
        testPluginRoot.setBitcoinWalletManager(mockBitcoinWalletManager);
        testPluginRoot.setCryptoVaultManager(mockCryptoVaultManager);
        testPluginRoot.setId(testId);

        CatchException.catchException(testPluginRoot).getTransactionManager();

        Assertions.assertThat(CatchException.caughtException()).isNull();


    }
}
*/
