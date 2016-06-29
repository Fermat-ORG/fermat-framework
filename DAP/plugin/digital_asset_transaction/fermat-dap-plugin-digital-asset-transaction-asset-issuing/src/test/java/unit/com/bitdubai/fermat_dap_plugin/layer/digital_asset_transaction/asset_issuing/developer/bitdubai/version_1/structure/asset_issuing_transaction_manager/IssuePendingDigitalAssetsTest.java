package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.asset_issuing_transaction_manager;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.functional.DigitalAssetCryptoTransactionFactory;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.AssetIssuingTransactionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.support.membermodification.MemberModifier;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;

/**
 * Created by frank on 26/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class IssuePendingDigitalAssetsTest {
    AssetIssuingTransactionManager assetIssuingTransactionManager;
    UUID pluginId;

    @Mock
    CryptoVaultManager cryptoVaultManager;

    @Mock
    CryptoWalletManager cryptoWalletManager;

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
    DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;


    String publicKey = "publicKey";

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        assetIssuingTransactionManager = new AssetIssuingTransactionManager(pluginId,
                cryptoVaultManager,
                cryptoWalletManager,
                pluginDatabaseSystem,
                pluginFileSystem,
                errorManager,
                assetVaultManager,
                cryptoAddressBookManager,
                outgoingIntraActorManager);
        assetIssuingTransactionManager.setPluginId(pluginId);

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
        MemberModifier.field(AssetIssuingTransactionManager.class, "digitalAssetCryptoTransactionFactory").set(assetIssuingTransactionManager, digitalAssetCryptoTransactionFactory);
        doNothing().when(digitalAssetCryptoTransactionFactory).issuePendingDigitalAssets(publicKey);
    }

    @Test
    public void test_OK() throws Exception {
        assetIssuingTransactionManager.issuePendingDigitalAssets(publicKey);
    }
}
