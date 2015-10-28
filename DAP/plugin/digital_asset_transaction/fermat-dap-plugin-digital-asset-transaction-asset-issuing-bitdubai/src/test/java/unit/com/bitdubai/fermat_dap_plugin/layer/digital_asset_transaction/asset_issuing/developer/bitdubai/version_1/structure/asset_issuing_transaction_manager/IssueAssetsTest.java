package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.asset_issuing_transaction_manager;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.AssetIssuingTransactionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetCryptoTransactionFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetIssuingVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.support.membermodification.MemberModifier;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 26/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class IssueAssetsTest {
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

    DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;

    @Mock
    DigitalAssetIssuingVault digitalAssetIssuingVault;

    DigitalAsset digitalAssetToIssue;

    @Mock
    DigitalAssetContract digitalAssetContract;

    @Mock
    List<Resource> resources;

    @Mock
    IdentityAssetIssuer identityAssetIssuer;

    @Mock
    AssetIssuingTransactionDao assetIssuingTransactionDao;

    @Mock
    PluginTextFile pluginTextFile;

    @Mock
    BitcoinWalletWallet bitcoinWalletWallet;

    @Mock
    BitcoinWalletBalance bitcoinWalletBalance;

    @Mock
    CryptoAddress cryptoAddress;

    BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.REG_TEST;
    final String LOCAL_STORAGE_PATH="digital-asset-issuing/";
    String path;
    int assetsAmount;
    String walletPublicKey;
    String daPublicKey = "daPublicKey";
    String name;
    String fileName;
    String transactionId = "defe0d66-cd65-4f8f-bc07-afa563815496";

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
        digitalAssetCryptoTransactionFactory.setDigitalAssetIssuingVault(digitalAssetIssuingVault);
        digitalAssetCryptoTransactionFactory.setAssetIssuingTransactionDao(assetIssuingTransactionDao);

        path = LOCAL_STORAGE_PATH + daPublicKey;
        name = "name";
        fileName = name + ".xml";

        digitalAssetToIssue = new DigitalAsset();
        digitalAssetToIssue.setContract(digitalAssetContract);
        digitalAssetToIssue.setResources(resources);
        digitalAssetToIssue.setDescription("description");
        digitalAssetToIssue.setName(name);
        digitalAssetToIssue.setPublicKey(daPublicKey);
        digitalAssetToIssue.setIdentityAssetIssuer(identityAssetIssuer);

        assetsAmount = 1;
        walletPublicKey = "publicKey";

        MemberModifier.field(AssetIssuingTransactionManager.class, "digitalAssetCryptoTransactionFactory").set(assetIssuingTransactionManager , digitalAssetCryptoTransactionFactory);
        MemberModifier.field(DigitalAssetCryptoTransactionFactory.class, "SEND_BTC_FROM_CRYPTO_VAULT").set(digitalAssetCryptoTransactionFactory , true);

        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
        when(assetIssuingTransactionDao.isPublicKeyUsed(daPublicKey)).thenReturn(false);
        when(pluginFileSystem.createTextFile(pluginId, path, fileName, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT)).thenReturn(pluginTextFile);
        doNothing().when(assetIssuingTransactionDao).persistDigitalAsset(daPublicKey,
                path,
                assetsAmount,
                blockchainNetworkType,
                walletPublicKey);
        when(bitcoinWalletManager.loadWallet(walletPublicKey)).thenReturn(bitcoinWalletWallet);
        when(bitcoinWalletBalance.getBalance()).thenReturn(anyLong());
        when(bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE)).thenReturn(bitcoinWalletBalance);
        when(assetVaultManager.getNewAssetVaultCryptoAddress(this.blockchainNetworkType)).thenReturn(cryptoAddress);
        doNothing().when(assetIssuingTransactionDao).persistGenesisAddress(transactionId, null);
        doNothing().when(cryptoAddressBookManager).registerCryptoAddress(null,
                "testDeliveredByActorPublicKey",
                Actors.INTRA_USER,
                "testDeliveredToActorPublicKey",
                Actors.DAP_ASSET_ISSUER,
                Platforms.DIGITAL_ASSET_PLATFORM,
                VaultType.ASSET_VAULT,
                CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                this.walletPublicKey,
                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
        doNothing().when(assetIssuingTransactionDao).updateDigitalAssetTransactionStatus(transactionId, TransactionStatus.GENESIS_SETTLED);

    }

    @Test
    public void test_OK() throws Exception {
        assetIssuingTransactionManager.issueAssets(digitalAssetToIssue, assetsAmount, walletPublicKey, blockchainNetworkType);
    }

    @Test
    public void test_Throws_CantIssueDigitalAssetsException() throws Exception {
        catchException(assetIssuingTransactionManager).issueAssets(digitalAssetToIssue, assetsAmount, walletPublicKey, null);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantIssueDigitalAssetsException.class);
        assertThat(thrown.getCause()).isInstanceOf(CantIssueDigitalAssetsException.class);
    }
}
