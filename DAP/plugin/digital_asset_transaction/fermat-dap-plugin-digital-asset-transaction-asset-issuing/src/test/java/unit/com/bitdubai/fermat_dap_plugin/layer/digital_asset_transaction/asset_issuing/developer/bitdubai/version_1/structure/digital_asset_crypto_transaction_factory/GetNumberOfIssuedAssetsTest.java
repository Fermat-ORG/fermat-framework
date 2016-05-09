package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.digital_asset_crypto_transaction_factory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.exceptions.CantCheckAssetIssuingProgressException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.functional.DigitalAssetCryptoTransactionFactory;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 27/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetNumberOfIssuedAssetsTest {
    DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;
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
    AssetVaultManager assetVaultManager;

    @Mock
    CryptoAddressBookManager cryptoAddressBookManager;

    @Mock
    OutgoingIntraActorManager outgoingIntraActorManager;

    @Mock
    ErrorManager errorManager;

    @Mock
    AssetIssuingDAO assetIssuingDAO;

    @Mock
    Database database;

    String assetPublicKey;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        digitalAssetCryptoTransactionFactory = new DigitalAssetCryptoTransactionFactory(this.pluginId,
                this.cryptoVaultManager,
                this.bitcoinWalletManager,
                this.pluginDatabaseSystem,
                this.pluginFileSystem,
                this.assetVaultManager,
                this.cryptoAddressBookManager,
                this.outgoingIntraActorManager);
        digitalAssetCryptoTransactionFactory.setErrorManager(errorManager);
        digitalAssetCryptoTransactionFactory.setAssetIssuingTransactionDao(assetIssuingDAO);

        MemberModifier.field(AssetIssuingDAO.class, "database").set(assetIssuingDAO, database);

        assetPublicKey = "assetPublicKey";
    }

    @Test
    public void test_OK() throws Exception {
        when(assetIssuingDAO.getNumberOfIssuedAssets(assetPublicKey)).thenReturn(5);

        int issuedAssets = digitalAssetCryptoTransactionFactory.getNumberOfIssuedAssets(assetPublicKey);
        assertThat(issuedAssets).isEqualTo(5);
    }

    @Test
    public void test_Throws_CantCheckAssetIssuingProgressException() throws Exception {
        when(assetIssuingDAO.getNumberOfIssuedAssets(assetPublicKey)).thenCallRealMethod();
        doThrow(new CantOpenDatabaseException("error")).when(database).openDatabase();

        catchException(digitalAssetCryptoTransactionFactory).getNumberOfIssuedAssets(assetPublicKey);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantCheckAssetIssuingProgressException.class);
        assertThat(thrown.getCause()).isInstanceOf(CantOpenDatabaseException.class);
    }
}
