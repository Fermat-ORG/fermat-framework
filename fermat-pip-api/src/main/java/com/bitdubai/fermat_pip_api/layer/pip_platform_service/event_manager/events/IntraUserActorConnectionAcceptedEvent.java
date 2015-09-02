package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.event.EventSource;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;

/**
 * Created by natalia on 17/08/15.
 */
public class IntraUserActorConnectionAcceptedEvent extends AbstractPlatformEvent {

    String intraUserLoggedInPublicKey;
    String intraUserToAddPublicKey;

    public IntraUserActorConnectionAcceptedEvent(EventType eventType){
        super(eventType);
    }

    /**
     * Constructor with parameters
     *
     * @param eventType
     * @param intraUserLoggedInPublicKey
     * @param intraUserToAddPublicKey
     */
    public IntraUserActorConnectionAcceptedEvent(EventType eventType,String intraUserLoggedInPublicKey, String intraUserToAddPublicKey) {
        super(eventType);

        this.intraUserLoggedInPublicKey = intraUserLoggedInPublicKey;
        this.intraUserToAddPublicKey = intraUserToAddPublicKey;
    }

    /**
     *
     * @return
     */
    public String getIntraUserLoggedInPublicKey() {
        return this.intraUserLoggedInPublicKey;
    }

    /**
     *
     * @return
     */
    public String getIntraUserToAddPublicKey() {
        return this.intraUserToAddPublicKey;
    }
}
