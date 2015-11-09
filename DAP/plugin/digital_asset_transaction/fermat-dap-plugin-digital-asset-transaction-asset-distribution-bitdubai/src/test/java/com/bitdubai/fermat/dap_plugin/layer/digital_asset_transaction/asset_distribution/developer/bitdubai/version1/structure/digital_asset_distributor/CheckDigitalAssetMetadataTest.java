package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.digital_asset_distributor;

import com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks.MockDigitalAssetMetadataForTesting;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantDeliverDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.DigitalAssetDistributionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.DigitalAssetDistributor;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 04/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CheckDigitalAssetMetadataTest {
    @Mock
    private ActorAssetIssuer actorAssetIssuer;
    @Mock
    private ActorAssetIssuerManager actorAssetIssuerManager;
    @Mock
    private ErrorManager errorManager;
    @Mock
    private PluginFileSystem pluginFileSystem;
    @Mock
    private BitcoinNetworkManager bitcoinNetworkManager;
    private UUID pluginId;
    private AssetDistributionDao assetDistributionDao = mock(AssetDistributionDao.class);
    private DigitalAssetDistributionVault digitalAssetDistributionVault;
    private MockDigitalAssetMetadataForTesting mockDigitalAssetMetadata;
    private DigitalAssetDistributor digitalAssetDistributor;
    private CryptoTransaction cryptoTransaction;
    private CryptoTransaction genesisTransaction;
    private List<CryptoTransaction> listGenesisTransaction;

    @Before
    public void init () throws Exception {
        pluginId = UUID.randomUUID();
        digitalAssetDistributionVault = new DigitalAssetDistributionVault(pluginId, pluginFileSystem, errorManager);
        digitalAssetDistributor = new DigitalAssetDistributor(errorManager, pluginId, pluginFileSystem);
        digitalAssetDistributor.setActorAssetIssuerManager(actorAssetIssuerManager);
        digitalAssetDistributor.setDigitalAssetDistributionVault(digitalAssetDistributionVault);
        digitalAssetDistributor.setWalletPublicKey("walletPublicKey");
        digitalAssetDistributor.setAssetDistributionDao(assetDistributionDao);
        digitalAssetDistributor.setBitcoinCryptoNetworkManager(bitcoinNetworkManager);


        mockDigitalAssetMetadata = new MockDigitalAssetMetadataForTesting();

        genesisTransaction = new CryptoTransaction();
        String typeCoin = CryptoCurrency.BITCOIN.getCode();
        BalanceType typeBalance = BalanceType.getByCode("AVAILABLE");
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
        listGenesisTransaction =  new ArrayList<CryptoTransaction>();
        listGenesisTransaction.add(genesisTransaction);
        cryptoTransaction=new CryptoTransaction(mockDigitalAssetMetadata.getGenesisTransaction(),
                mockDigitalAssetMetadata.getDigitalAsset().getGenesisAddress(),
                addressTo,CryptoCurrency.BITCOIN, 100000,CryptoStatus.ON_BLOCKCHAIN);
        cryptoTransaction.setOp_Return(mockDigitalAssetMetadata.getDigitalAssetHash());
        digitalAssetDistributor.cryptoTransaction = cryptoTransaction;
        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
        when(bitcoinNetworkManager.getGenesisTransaction(mockDigitalAssetMetadata.getGenesisTransaction())).thenReturn(listGenesisTransaction);
    }

    @Test
    public void checkDigitalAssetMetadataTest () throws CantDeliverDigitalAssetException {
        digitalAssetDistributor.checkDigitalAssetMetadata(mockDigitalAssetMetadata);
    }

    /*@Test
    public void checkDigitalAssetMetadataThrowsCantGetGenesisTransactionExceptionListGenesisTransactionNullTest () throws CantDeliverDigitalAssetException, CantGetGenesisTransactionException {
        when(bitcoinNetworkManager.getGenesisTransaction(mockDigitalAssetMetadata.getGenesisTransaction())).thenReturn(null);
        catchException(digitalAssetDistributor).checkDigitalAssetMetadata(mockDigitalAssetMetadata);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantDeliverDigitalAssetException.class);
    }

    /*@Test
    public void checkDigitalAssetMetadataThrowsCantGetGenesisTransactionExceptionDigitalAssetModifyTest () throws CantDeliverDigitalAssetException, CantGetGenesisTransactionException {
        cryptoTransaction.setOp_Return("ffg55dsds55dsdsrfeetgga5");
        digitalAssetDistributor.cryptoTransaction = cryptoTransaction;
        catchException(digitalAssetDistributor).checkDigitalAssetMetadata(mockDigitalAssetMetadata);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantDeliverDigitalAssetException.class);
    }*/
}
