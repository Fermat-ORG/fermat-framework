package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.digital_asset_distribution_vault;

import com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks.MockDigitalAssetMetadataForTesting;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.AssetDistributionTransactionManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.DigitalAssetDistributionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.DigitalAssetDistributor;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

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

    @Before
    public void init() throws Exception {
        pluginId = UUID.randomUUID();
        mockDigitalAssetDistributionVault = new DigitalAssetDistributionVault(pluginId, pluginFileSystem, errorManager);
        digitalAssetMetadata = new MockDigitalAssetMetadataForTesting();
        genesisTransaction = new CryptoTransaction();
        String typeCoin = CryptoCurrency.BITCOIN.getCode();
        System.out.println("Type coin: "+typeCoin);
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
    }

    @Test
    public void setDigitalAssetMetadataAssetIssuerWalletDebitSuccesFullTest() throws Exception {
        System.out.println("Test method setDigitalAssetMetadataAssetIssuerWalletDebitSuccesFullTest()");
        BalanceType typeBalance = BalanceType.getByCode("AVAILABLE");
        catchException(mockDigitalAssetDistributionVault).setDigitalAssetMetadataAssetIssuerWalletDebit(digitalAssetMetadata, genesisTransaction, typeBalance);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNull();
    }
}
