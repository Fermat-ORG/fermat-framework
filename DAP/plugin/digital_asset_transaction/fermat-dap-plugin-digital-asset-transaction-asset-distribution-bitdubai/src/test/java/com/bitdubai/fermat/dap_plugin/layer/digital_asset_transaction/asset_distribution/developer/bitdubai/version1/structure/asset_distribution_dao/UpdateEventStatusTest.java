package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_dao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 03/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateEventStatusTest {
    @Mock
    private AssetDistributionDao mockAssetDistributionDao;

    @Test
    public void updateEventStatusTest () throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
        mockAssetDistributionDao.updateEventStatus("eventId");
    }
}
