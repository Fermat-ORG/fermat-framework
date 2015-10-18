/*
* @#ReceivedNewDigitalAssetMetadataNotificationEventListener.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_api.layer.all_definition.listeners;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DapEvenType;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.all_definition.listeners.ReceivedNewDigitalAssetMetadataNotificationEventListener</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 11/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ReceivedNewDigitalAssetMetadataNotificationEventListener extends BasicFermatEventListener {

    public ReceivedNewDigitalAssetMetadataNotificationEventListener(DapEvenType eventType, FermatEventMonitor eventMonitor) {
        super(eventType, eventMonitor);
    }
}
