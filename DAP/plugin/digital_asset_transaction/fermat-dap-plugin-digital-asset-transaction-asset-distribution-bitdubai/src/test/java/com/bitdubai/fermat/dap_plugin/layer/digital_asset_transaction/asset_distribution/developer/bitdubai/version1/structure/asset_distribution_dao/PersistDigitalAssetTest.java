package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_dao;

import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 02/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class PersistDigitalAssetTest {
    private AssetDistributionDao mockAssetDistributionDao;

    @Before
    public void init () throws CantExecuteDatabaseOperationException {
        mockAssetDistributionDao = mock(AssetDistributionDao.class);
    }

    @Test
    public void persistDigitalAssetTest () throws CantPersistDigitalAssetException {
        String genesisTransaction = "d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        String localStoragePath = "localStoragePath";
        String digitalAssetHash = "d21633ba23f70118185227be5";
        String actorReceiverPublicKey = "ASDS-16988807";
        String actorReceiverBitcoinAddress = "ASDS-16988807";
        mockAssetDistributionDao.persistDigitalAsset(genesisTransaction, localStoragePath, digitalAssetHash, actorReceiverPublicKey, actorReceiverBitcoinAddress);
    }

}
