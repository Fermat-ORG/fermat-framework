package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.asset_issuing_transaction_manager;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.AssetIssuingTransactionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;

/**
 * Created by frank on 26/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConfirmReceptionTest {
    AssetIssuingTransactionManager assetIssuingTransactionManager;
    UUID pluginId;

    @Mock
    CryptoVaultManager cryptoVaultManager;

    @Mock
    BitcoinWalletManager bitcoinWalletManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    ErrorManager errorManager;

    @Mock
    AssetVaultManager assetVaultManager;

    @Mock
    CryptoAddressBookManager cryptoAddressBookManager;

    @Mock
    OutgoingIntraActorManager outgoingIntraActorManager;

    @Mock
    AssetIssuingTransactionDao assetIssuingTransactionDao;

    @Mock
    Database database;


    String genesisTransaction = "genesisTransaction";

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        assetIssuingTransactionManager = new AssetIssuingTransactionManager(pluginId,
                cryptoVaultManager,
                bitcoinWalletManager,
                pluginDatabaseSystem,
                pluginFileSystem,
                errorManager,
                assetVaultManager,
                cryptoAddressBookManager,
                outgoingIntraActorManager);
        assetIssuingTransactionManager.setPluginId(pluginId);
        assetIssuingTransactionManager.setAssetIssuingTransactionDao(assetIssuingTransactionDao);
    }

    @Test
    public void test_OK() throws Exception {
        assetIssuingTransactionManager.confirmReception(genesisTransaction);
    }

    @Test
    public void test_Throws_CantExecuteQueryException() throws Exception {
        doThrow(new CantExecuteQueryException("error")).when(assetIssuingTransactionDao).confirmReception(genesisTransaction);

        catchException(assetIssuingTransactionManager).confirmReception(genesisTransaction);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantConfirmTransactionException.class);
        assertThat(thrown.getCause()).isInstanceOf(CantExecuteQueryException.class);
    }

    @Test
    public void test_Throws_UnexpectedResultReturnedFromDatabaseException() throws Exception {
        doThrow(new UnexpectedResultReturnedFromDatabaseException("error", "")).when(assetIssuingTransactionDao).confirmReception(genesisTransaction);

        catchException(assetIssuingTransactionManager).confirmReception(genesisTransaction);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantConfirmTransactionException.class);
        assertThat(thrown.getCause()).isInstanceOf(UnexpectedResultReturnedFromDatabaseException.class);
    }
}
