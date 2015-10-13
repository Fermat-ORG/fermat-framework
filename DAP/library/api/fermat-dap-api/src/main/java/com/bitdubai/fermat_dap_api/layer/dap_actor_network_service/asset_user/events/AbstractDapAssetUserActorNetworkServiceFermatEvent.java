/*
* @#AbstractDapAssetUserActorNetworkServiceFermatEvent.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.events;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.enums.EventTypeAssetUserANS;

/**
 * The Class <code>AbstractDapAssetUserActorNetworkServiceFermatEvent</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 11/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AbstractDapAssetUserActorNetworkServiceFermatEvent implements FermatEvent {

    private final EventTypeAssetUserANS eventType;

    private EventSource eventSource;

    public AbstractDapAssetUserActorNetworkServiceFermatEvent(EventTypeAssetUserANS eventType){

        this.eventType = eventType;
    }

    @Override
    public EventTypeAssetUserANS getEventType() {
        return this.eventType;
    }

    @Override
    public void setSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    @Override
    public EventSource getSource() {
        return this.eventSource;
    }
}
