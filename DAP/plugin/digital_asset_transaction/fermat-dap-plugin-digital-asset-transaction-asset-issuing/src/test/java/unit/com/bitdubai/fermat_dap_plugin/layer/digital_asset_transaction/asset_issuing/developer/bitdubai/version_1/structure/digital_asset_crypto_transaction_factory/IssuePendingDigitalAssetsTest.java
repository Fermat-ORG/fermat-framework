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
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.functional.DigitalAssetCryptoTransactionFactory;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.support.membermodification.MemberModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by frank on 27/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class IssuePendingDigitalAssetsTest {
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

    List<String> pendingDigitalAssetsTransactionIdList;

    DigitalAsset digitalAsset;

    @Mock
    IdentityAssetIssuer identityAssetIssuer;

    @Mock
    DigitalAssetContract digitalAssetContract;

    @Mock
    PluginTextFile pluginTextFile;

    @Mock
    CryptoAddress genesisAddress;

    @Mock
    List<Resource> resources;

    String transactionId;
    String digitalAssetPublicKey;
    String publicKey;
    String name;
    String description;
    String digitalAssetFileStoragePath;
    String digitalAssetFileName;

    BlockchainNetworkType blockchainNetworkType;


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

        digitalAsset = new DigitalAsset();
        digitalAsset.setIdentityAssetIssuer(identityAssetIssuer);
        digitalAsset.setPublicKey(publicKey);
        digitalAsset.setName(name);
        digitalAsset.setDescription(description);
        digitalAsset.setContract(digitalAssetContract);
        digitalAsset.setResources(resources);

        blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

        MemberModifier.field(DigitalAssetCryptoTransactionFactory.class, "digitalAsset").set(digitalAssetCryptoTransactionFactory, digitalAsset);
        MemberModifier.field(DigitalAssetCryptoTransactionFactory.class, "blockchainNetworkType").set(digitalAssetCryptoTransactionFactory, blockchainNetworkType);

        pendingDigitalAssetsTransactionIdList = new ArrayList();
        pendingDigitalAssetsTransactionIdList.add(transactionId);

        publicKey = "publicKey";
        transactionId = "transaction1";
        digitalAssetPublicKey = "digitalAssetPublicKey";
        name = "name";
        description = "description";
        digitalAssetFileStoragePath = "digital-asset-issuing/digitalAssetPublicKey";
        digitalAssetFileName = "null.xml";

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(assetIssuingDAO.getPendingDigitalAssetsTransactionIdByPublicKey(publicKey)).thenReturn(pendingDigitalAssetsTransactionIdList);
        when(assetIssuingDAO.getDigitalAssetTransactionStatus(null)).thenReturn(TransactionStatus.FORMING_GENESIS);
        when(assetIssuingDAO.getDigitalAssetPublicKeyById(null)).thenReturn(digitalAssetPublicKey);
        when(pluginFileSystem.getTextFile(pluginId, digitalAssetFileStoragePath, digitalAssetFileName, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(pluginTextFile);
        when(assetVaultManager.getNewAssetVaultCryptoAddress(blockchainNetworkType)).thenReturn(genesisAddress);
    }

    @Test
    public void testOK() throws Exception {
        //TODO solve recursive call
//        digitalAssetCryptoTransactionFactory.issuePendingDigitalAssets(publicKey);
    }
}
