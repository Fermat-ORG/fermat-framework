package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

/**
 * Created by natalia on 17/08/15.
 */
public class IntraUserActorConnectionAcceptedEvent extends AbstractFermatEvent {

    String intraUserLoggedInPublicKey;
    String intraUserToAddPublicKey;

    public IntraUserActorConnectionAcceptedEvent(com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType eventType){
        super(eventType);
    }



    public void setIntraUserLoggedInPublicKey(String intraUserLoggedInPublicKey) {
        this.intraUserLoggedInPublicKey = intraUserLoggedInPublicKey;
    }

    public void setIntraUserToAddPublicKey(String intraUserToAddPublicKey) {
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
