package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.received_new_digital_asset_metadata_notification_event_handler;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events.ReceivedNewDigitalAssetMetadataNotificationEventHandler;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;


/**
 * Created by lcampo on 11/11/15.
 */
public class SetAssetDistributionRecorderServiceTest {
    private ReceivedNewDigitalAssetMetadataNotificationEventHandler receivedNewDigitalAssetMetadataNotificationEventHandler;

    @Test
    public void setAssetDistributionRecorderServiceThrowCantSetObjectExceptionTest() throws CantSetObjectException {
        receivedNewDigitalAssetMetadataNotificationEventHandler = new ReceivedNewDigitalAssetMetadataNotificationEventHandler();
        try {
            receivedNewDigitalAssetMetadataNotificationEventHandler.setAssetDistributionRecorderService(null);
            fail("The method didn't throw when I expected it to");
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof CantSetObjectException);
        }
    }
}
