package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.incoming_asset_on_crypto_network_waiting_transference_asset_user_event_handler;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events.IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventHandler;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;


/**
 * Created by lcampo on 11/11/15.
 */
public class SetAssetDistributionRecorderServiceTest {
    private IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventHandler incomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventHandler;

    @Test
    public void setAssetDistributionRecorderServiceThrowCantSetObjectExceptionTest() throws CantSetObjectException {
        incomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventHandler = new IncomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventHandler();
        try {
            incomingAssetOnCryptoNetworkWaitingTransferenceAssetUserEventHandler.setAssetDistributionRecorderService(null);
            fail("The method didn't throw when I expected it to");
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof CantSetObjectException);
        }
    }
}
