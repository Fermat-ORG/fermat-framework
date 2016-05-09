package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.digital_asset_issuing_vault;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.DigitalAssetIssuingVault;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by frank on 28/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class DeliverDigitalAssetMetadataToAssetWallet {
    DigitalAssetIssuingVault digitalAssetIssuingVault;
    UUID pluginId;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    ErrorManager errorManager;

    @Mock
    CryptoTransaction genesisTransaction;

    AssetBalanceType assetBalanceType;

    @Mock
    PluginTextFile digitalAssetMetadataFile;

    @Mock
    AssetIssuerWalletManager assetIssuerWalletManager;

    @Mock
    AssetIssuerWallet assetIssuerWallet;

    @Mock
    AssetIssuerWalletBalance assetIssuerWalletBalance;

    @Mock
    DigitalAssetMetadata digitalAssetMetadata;

    BalanceType balanceType;

    String internalId;
    String LOCAL_STORAGE_PATH;
    String digitalAssetMetadataFileName;
    FilePrivacy FILE_PRIVACY;
    FileLifeSpan FILE_LIFE_SPAN;
    String walletPublicKey;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        digitalAssetIssuingVault = new DigitalAssetIssuingVault(pluginId, pluginFileSystem, errorManager);
        digitalAssetIssuingVault.setAssetIssuerWalletManager(assetIssuerWalletManager);

        assetBalanceType = AssetBalanceType.AVAILABLE;
        balanceType = BalanceType.AVAILABLE;
        internalId = "internalId";
        LOCAL_STORAGE_PATH="digital-asset-swap/";
        digitalAssetMetadataFileName = internalId + ".xml";
        FILE_PRIVACY=FilePrivacy.PUBLIC;
        FILE_LIFE_SPAN=FileLifeSpan.PERMANENT;
        walletPublicKey = "walletPublicKeyTest";

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(pluginFileSystem.getTextFile(this.pluginId, this.LOCAL_STORAGE_PATH + "digital-asset-metadata/", digitalAssetMetadataFileName, FILE_PRIVACY, FILE_LIFE_SPAN)).thenReturn(digitalAssetMetadataFile);
        when(assetIssuerWalletManager.loadAssetIssuerWallet(walletPublicKey)).thenReturn(assetIssuerWallet);
        when(assetIssuerWallet.getBookBalance(balanceType)).thenReturn(assetIssuerWalletBalance);
    }

    @Test
    public void test_OK() throws Exception {
        //TODO solve problem in getDigitalAssetMetadataFromLocalStorage method
//        digitalAssetIssuingVault.deliverDigitalAssetMetadataToAssetWallet(genesisTransaction, internalId, assetBalanceType);
    }
}
