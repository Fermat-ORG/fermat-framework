package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.asset_issuing_transaction_manager;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManagerPlatform;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.PlatformCryptoVaultManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.AssetIssuingTransactionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetCryptoTransactionFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.support.membermodification.MemberModifier;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 26/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetNumberOfIssuedAssetsTest {
    AssetIssuingTransactionManager assetIssuingTransactionManager;
    UUID pluginId;

    @Mock
    PlatformCryptoVaultManager cryptoVaultManager;

    @Mock
    BitcoinWalletManager bitcoinWalletManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    ErrorManager errorManager;

    @Mock
    AssetVaultManagerPlatform assetVaultManager;

    @Mock
    CryptoAddressBookManager cryptoAddressBookManager;

    @Mock
    OutgoingIntraActorManager outgoingIntraActorManager;

    DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;

    @Mock
    AssetIssuingTransactionDao assetIssuingTransactionDao;

    String assetPublicKey = "assetPublicKey";

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

        digitalAssetCryptoTransactionFactory = new DigitalAssetCryptoTransactionFactory(this.pluginId,
                this.cryptoVaultManager,
                this.bitcoinWalletManager,
                this.pluginDatabaseSystem,
                this.pluginFileSystem,
                this.assetVaultManager,
                this.cryptoAddressBookManager,
                this.outgoingIntraActorManager);
        digitalAssetCryptoTransactionFactory.setErrorManager(errorManager);
        digitalAssetCryptoTransactionFactory.setAssetIssuingTransactionDao(assetIssuingTransactionDao);
        MemberModifier.field(AssetIssuingTransactionManager.class, "digitalAssetCryptoTransactionFactory").set(assetIssuingTransactionManager, digitalAssetCryptoTransactionFactory);

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {

    }

    @Test
    public void test_OK() throws Exception {
        when(assetIssuingTransactionDao.getNumberOfIssuedAssets(assetPublicKey)).thenReturn(5);

        int issuedAssets = assetIssuingTransactionManager.getNumberOfIssuedAssets(assetPublicKey);
        assertThat(issuedAssets).isEqualTo(5);
    }

    @Test
    public void test_Throws_CantExecuteDatabaseOperationException() throws Exception {
        when(assetIssuingTransactionDao.getNumberOfIssuedAssets(assetPublicKey)).thenThrow(new CantCheckAssetIssuingProgressException("error"));

        catchException(assetIssuingTransactionManager).getNumberOfIssuedAssets(assetPublicKey);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantExecuteDatabaseOperationException.class);
        assertThat(thrown.getCause()).isInstanceOf(CantCheckAssetIssuingProgressException.class);
    }
}
