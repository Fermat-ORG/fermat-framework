package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.digital_asset_distributor;

import com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks.MockActorAssetIssuerManager;
import com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks.MockDigitalAssetMetadataForTesting;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.developer_utils.mocks.MockActorAssetUserForTesting;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.DigitalAssetDistributionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.DigitalAssetDistributor;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 04/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class DistributeAssetsTest {
    @Mock
    private ActorAssetIssuer actorAssetIssuer;
    @Mock
    private MockActorAssetIssuerManager actorAssetIssuerManager;
    @Mock
    private ErrorManager errorManager;
    @Mock
    private PluginFileSystem pluginFileSystem;
    @Mock
    private BitcoinNetworkManager bitcoinNetworkManager;
    @Mock
    private AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;
    @Mock
    private PluginTextFile digitalAssetFile;

    private UUID pluginId;
    private AssetDistributionDao assetDistributionDao = mock(AssetDistributionDao.class);
    private DigitalAssetDistributionVault digitalAssetDistributionVault;
    private MockDigitalAssetMetadataForTesting mockDigitalAssetMetadata;
    private DigitalAssetDistributor digitalAssetDistributor;
    private CryptoTransaction cryptoTransaction;
    private CryptoTransaction genesisTransaction;
    private List<CryptoTransaction> listGenesisTransaction;
    private HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToDistribute;

    private FilePrivacy FILE_PRIVACY;
    private FileLifeSpan FILE_LIFE_SPAN;
    private String fileName = "file.xml";
    private String digitalAssetFileStoragePath = "digital-asset-transmission/digital-asset";


    @Before
    public void init() throws Exception {
        FILE_PRIVACY = FilePrivacy.getByCode(FilePrivacy.PUBLIC.getCode());
        FILE_LIFE_SPAN = FileLifeSpan.getByCode(FileLifeSpan.PERMANENT.getCode());
        pluginId = UUID.randomUUID();
        actorAssetIssuerManager = new MockActorAssetIssuerManager();
        digitalAssetDistributionVault = Mockito.mock(DigitalAssetDistributionVault.class);//new DigitalAssetDistributionVault(pluginId, pluginFileSystem, errorManager);
        digitalAssetDistributor = new DigitalAssetDistributor(errorManager, pluginId, pluginFileSystem);
        digitalAssetDistributor.setActorAssetIssuerManager(actorAssetIssuerManager);
        digitalAssetDistributor.setDigitalAssetDistributionVault(digitalAssetDistributionVault);
        digitalAssetDistributor.setWalletPublicKey("walletPublicKey");
        digitalAssetDistributor.setAssetDistributionDao(assetDistributionDao);
        digitalAssetDistributor.setBitcoinCryptoNetworkManager(bitcoinNetworkManager);
        digitalAssetDistributor.setAssetTransmissionNetworkServiceManager(assetTransmissionNetworkServiceManager);

        mockDigitalAssetMetadata = new MockDigitalAssetMetadataForTesting();

        genesisTransaction = new CryptoTransaction();
        String typeCoin = CryptoCurrency.BITCOIN.getCode();
        BalanceType typeBalance = BalanceType.getByCode("AVAILABLE");
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
        listGenesisTransaction = new ArrayList<CryptoTransaction>();
        listGenesisTransaction.add(genesisTransaction);
        cryptoTransaction = new CryptoTransaction(mockDigitalAssetMetadata.getGenesisTransaction(),
                mockDigitalAssetMetadata.getDigitalAsset().getGenesisAddress(),
                addressTo, CryptoCurrency.BITCOIN, 100000, CryptoStatus.ON_BLOCKCHAIN);
        cryptoTransaction.setOp_Return(mockDigitalAssetMetadata.getDigitalAssetHash());
        digitalAssetDistributor.cryptoTransaction = cryptoTransaction;

        ActorAssetUser actorAssetUser = new MockActorAssetUserForTesting();
        digitalAssetsToDistribute = new HashMap<DigitalAssetMetadata, ActorAssetUser>();
        digitalAssetsToDistribute.put(mockDigitalAssetMetadata, actorAssetUser);
        //setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
        when(bitcoinNetworkManager.getCryptoTransaction(mockDigitalAssetMetadata.getGenesisTransaction())).thenReturn(listGenesisTransaction);
    }

    @Test
    public void distributeAssetsTest() throws CantDistributeDigitalAssetsException {
        digitalAssetDistributor.distributeAssets(digitalAssetsToDistribute);
    }

    @Test
    public void distributeAssetsTestThrowsCCantDistributeDigitalAssetsExceptionTest() throws CantDistributeDigitalAssetsException, CantGetCryptoTransactionException {
        when(bitcoinNetworkManager.getCryptoTransaction(mockDigitalAssetMetadata.getGenesisTransaction())).thenReturn(null);

        try {
            digitalAssetDistributor.distributeAssets(null);
            fail("The method didn't throw when I expected it to");
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof CantDistributeDigitalAssetsException);
        }
    }

}
