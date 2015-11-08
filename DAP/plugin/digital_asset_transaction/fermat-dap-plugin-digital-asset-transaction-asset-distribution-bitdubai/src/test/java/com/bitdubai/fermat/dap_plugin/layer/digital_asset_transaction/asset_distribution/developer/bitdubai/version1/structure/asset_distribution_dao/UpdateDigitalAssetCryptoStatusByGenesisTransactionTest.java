package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_dao;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantCheckAssetDistributionProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 03/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateDigitalAssetCryptoStatusByGenesisTransactionTest {
    @Mock
    private AssetDistributionDao mockAssetDistributionDao;

    @Test
    public void updateDigitalAssetCryptoStatusByGenesisTransactionTest () throws CantCheckAssetDistributionProgressException, UnexpectedResultReturnedFromDatabaseException {
        String genesisTransaction = "d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43";
        mockAssetDistributionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.PENDING_SUBMIT);
    }
}
