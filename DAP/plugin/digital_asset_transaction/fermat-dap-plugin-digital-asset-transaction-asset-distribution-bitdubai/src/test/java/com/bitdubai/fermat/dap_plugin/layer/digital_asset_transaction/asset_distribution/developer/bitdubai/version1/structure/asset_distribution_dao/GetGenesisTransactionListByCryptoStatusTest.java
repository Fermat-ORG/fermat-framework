package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_dao;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantCheckAssetDistributionProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 03/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetGenesisTransactionListByCryptoStatusTest {
    private AssetDistributionDao mockAssetDistributionDao;

    @Before
    public void init () throws Exception {
        mockAssetDistributionDao = mock(AssetDistributionDao.class);
    }

    @Test
    public void getEventTypeByIdTest () throws CantCheckAssetDistributionProgressException {
        List<String> list = mockAssetDistributionDao.getGenesisTransactionListByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
        assertThat(list).isNotNull();
    }
}
