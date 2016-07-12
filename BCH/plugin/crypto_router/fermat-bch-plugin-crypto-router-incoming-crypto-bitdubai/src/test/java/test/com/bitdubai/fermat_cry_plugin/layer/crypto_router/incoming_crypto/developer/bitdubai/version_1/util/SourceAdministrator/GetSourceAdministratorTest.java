package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SourceAdministrator;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantIdentifyEventSourceException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SourceAdministrator;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;


@RunWith(MockitoJUnitRunner.class)
public class GetSourceAdministratorTest extends TestCase {

    @Mock
    CryptoVaultManager cryptoVault;

    @Mock
    TransactionProtocolManager<CryptoTransaction> transactionTransactionProtocolManager;

    SourceAdministrator sourceAdministrator;

    EventSource eventSource;


    @Before
    public void setUp() throws Exception {
        sourceAdministrator = new SourceAdministrator();
        sourceAdministrator.setCryptoVaultManager(cryptoVault);

        eventSource = EventSource.CRYPTO_VAULT;
        doReturn(transactionTransactionProtocolManager).when(cryptoVault).getTransactionManager();
    }


    // test if you get a source administrator
    @Test
    public void testGetSourceAdministrator_NotNull() throws Exception {
        TransactionProtocolManager<CryptoTransaction> transactionProtocolManager = sourceAdministrator.getSourceAdministrator(eventSource);
        assertNotNull(transactionProtocolManager);
    }

    // test if an eventSource cannot be identified
    @Test(expected = CantIdentifyEventSourceException.class)
    public void testGetSourceAdministrator_CantIdentifyEventSourceException() throws Exception {
        eventSource = EventSource.CRYPTO_ADDRESS_BOOK;

        sourceAdministrator.getSourceAdministrator(eventSource);
    }
}
