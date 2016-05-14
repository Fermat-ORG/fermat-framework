package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.digital_asset_crypto_transaction_factory;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletBalance;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.IntraActorCryptoTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.functional.DigitalAssetCryptoTransactionFactory;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.DigitalAssetIssuingVault;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 27/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class IssueDigitalAssetsTest {
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

    String assetPublicKey;

    @Mock
    DigitalAssetContract digitalAssetContract;

    @Mock
    List<Resource> resources;

    @Mock
    IdentityAssetIssuer identityAssetIssuer;

    DigitalAsset digitalAsset;

    @Mock
    DigitalAssetIssuingVault digitalAssetIssuingVault;

    @Mock
    PluginTextFile pluginTextFile;

    BlockchainNetworkType blockchainNetworkType;

    @Mock
    BitcoinWalletBalance bitcoinWalletBalance;

    @Mock
    CryptoAddress genesisAddress;

    @Mock
    IntraActorCryptoTransactionManager intraActorCryptoTransactionManager;

    int assetsAmount;
    String walletPublicKey;
    String description;
    String name;
    String publicKey;
    String digitalAssetFileStoragePath;
    String digitalAssetFileName;

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
        digitalAssetCryptoTransactionFactory.setDigitalAssetIssuingVault(digitalAssetIssuingVault);

        assetsAmount = 1;
        walletPublicKey = "walletPublicKey";
        description = "description";
        name = "name";
        publicKey = "publicKey";
        digitalAssetFileStoragePath = "digital-asset-issuing/publicKey";
        digitalAssetFileName = "name.xml";

        digitalAsset = new DigitalAsset();
        digitalAsset.setIdentityAssetIssuer(identityAssetIssuer);
        digitalAsset.setPublicKey(publicKey);
        digitalAsset.setName(name);
        digitalAsset.setDescription(description);
        digitalAsset.setContract(digitalAssetContract);
        digitalAsset.setResources(resources);

        blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

        MemberModifier.field(DigitalAssetCryptoTransactionFactory.class, "digitalAsset").set(digitalAssetCryptoTransactionFactory, digitalAsset);
        MemberModifier.field(DigitalAssetCryptoTransactionFactory.class, "bitcoinWalletBalance").set(digitalAssetCryptoTransactionFactory, bitcoinWalletBalance);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(pluginFileSystem.createTextFile(pluginId, digitalAssetFileStoragePath, digitalAssetFileName, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT)).thenReturn(pluginTextFile);
        doNothing().when(assetIssuingDAO).persistDigitalAsset(
                digitalAsset.getPublicKey(),
                this.digitalAssetFileStoragePath,
                this.assetsAmount,
                this.blockchainNetworkType,
                this.walletPublicKey);
        when(assetVaultManager.getNewAssetVaultCryptoAddress(blockchainNetworkType)).thenReturn(genesisAddress);
        when(outgoingIntraActorManager.getTransactionManager()).thenReturn(intraActorCryptoTransactionManager);
    }

    @Test
    public void test_OK() throws Exception {
        digitalAssetCryptoTransactionFactory.issueDigitalAssets(digitalAsset, assetsAmount, walletPublicKey, blockchainNetworkType);
    }

    @Test
    public void test_Throws_CantIssueDigitalAssetsException() throws Exception {
        catchException(digitalAssetCryptoTransactionFactory).issueDigitalAssets(digitalAsset, assetsAmount, walletPublicKey, null);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantIssueDigitalAssetsException.class);
        assertThat(thrown.getCause()).isInstanceOf(ObjectNotSetException.class);
    }
}
