package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.digital_asset_distribution_vault;

import com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks.MockDigitalAssetMetadataForTesting;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional.DigitalAssetDistributionVault;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 29/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetDigitalAssetMetadataAssetIssuerWalletDebitTest {
    private DigitalAssetDistributionVault mockDigitalAssetDistributionVault;
    private UUID pluginId;
    @Mock
    private ErrorManager errorManager;
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    private PluginFileSystem pluginFileSystem;
    private DigitalAssetMetadata digitalAssetMetadata;
    private CryptoTransaction genesisTransaction;

    @Mock
    private ActorAssetIssuerManager actorAssetIssuerManager;
    @Mock
    private ActorAssetIssuer actorAssetIssuer;
    @Mock
    private AssetIssuerWalletManager assetIssuerWalletManager;
    @Mock
    AssetIssuerWalletBalance assetIssuerWalletBalance;
    @Mock
    private AssetIssuerWallet assetIssuerWallet;
    private BalanceType typeBalance;

    @Before
    public void init() throws Exception {
        pluginId = UUID.randomUUID();
        mockDigitalAssetDistributionVault = new DigitalAssetDistributionVault(pluginId, pluginFileSystem, errorManager);
        mockDigitalAssetDistributionVault.walletPublicKey = "walletPublicKey";
        mockDigitalAssetDistributionVault.setActorAssetIssuerManager(actorAssetIssuerManager);
        mockDigitalAssetDistributionVault.setAssetIssuerWalletManager(assetIssuerWalletManager);

        digitalAssetMetadata = new MockDigitalAssetMetadataForTesting();
        genesisTransaction = new CryptoTransaction();
        String typeCoin = CryptoCurrency.BITCOIN.getCode();
        typeBalance = BalanceType.getByCode("AVAILABLE");
        System.out.println("Type coin: " + typeCoin);
        CryptoCurrency cryptoCurrency = CryptoCurrency.getByCode(typeCoin);
        CryptoAddress addressFrom = new CryptoAddress("mxJJSdXdKQLS4NeX6Y8tXFFoNASQnBShtv", cryptoCurrency);
        CryptoAddress addressTo = new CryptoAddress("mxJJSdXdKQLS4NeX6Y8tXFFoNASQnBShtv", cryptoCurrency);
        genesisTransaction.setAddressFrom(addressFrom);
        genesisTransaction.setAddressTo(addressTo);
        genesisTransaction.setCryptoAmount(10);
        genesisTransaction.setCryptoCurrency(cryptoCurrency);
        genesisTransaction.setCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
        genesisTransaction.setTransactionHash("d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43");
        System.out.println("Initialized data successfully");
        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
        when(assetIssuerWallet.getBookBalance(typeBalance)).thenReturn(assetIssuerWalletBalance);
        when(actorAssetIssuerManager.getActorAssetIssuer()).thenReturn(actorAssetIssuer);
        when(assetIssuerWalletManager.loadAssetIssuerWallet("walletPublicKey")).thenReturn(assetIssuerWallet);
    }

    @Test
    public void setDigitalAssetMetadataAssetIssuerWalletDebitSuccesFullTest() throws Exception {
        System.out.println("Test method setDigitalAssetMetadataAssetIssuerWalletDebitSuccesFullTest()");

        mockDigitalAssetDistributionVault.updateIssuerWalletBalance(digitalAssetMetadata, genesisTransaction, typeBalance);
    }


}
