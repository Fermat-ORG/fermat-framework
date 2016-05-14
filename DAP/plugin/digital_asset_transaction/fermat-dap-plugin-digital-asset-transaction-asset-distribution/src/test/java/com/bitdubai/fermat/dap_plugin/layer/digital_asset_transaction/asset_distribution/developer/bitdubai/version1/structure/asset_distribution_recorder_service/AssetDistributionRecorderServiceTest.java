package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_recorder_service;

import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.events.AssetDistributionRecorderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.fail;

/**
 * Created by lcampo on 11/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssetDistributionRecorderServiceTest {
    @Mock
    private EventManager eventManager;
    private AssetDistributionRecorderService assetDistributionRecorderService;
    @Test
    public void assetDistributionRecorderServiceThrowsCantSetObjectExceptionTest () throws CantStartServiceException {
        try {
            assetDistributionRecorderService = new AssetDistributionRecorderService(null, eventManager);
            fail("The method didn't throw when I expected it to");
        } catch (Exception ex){
            Assert.assertTrue(ex instanceof CantStartServiceException);
        }

    }
}
