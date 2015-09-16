package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by natalia on 17/08/15.
 */
public class IntraUserActorConnectionCancelledEvent extends AbstractFermatEvent {

    String intraUserLoggedInPublicKey;
    String intraUserToAddPublicKey;

    public IntraUserActorConnectionCancelledEvent(EventType eventType){
        super(eventType);
    }



    public void setIntraUserLoggedInPublicKey(String intraUserLoggedInPublicKey) {
        this.intraUserLoggedInPublicKey = intraUserLoggedInPublicKey;
    }

    public void setIntraUserToAddPublicKey(String intraUserToAddPublicKey) {
        this.intraUserToAddPublicKey = intraUserToAddPublicKey;
    }


    /**
     *Return the public key of intra user logged
     * @return String intra User Logged In PublicKey
     */
    public String getIntraUserLoggedInPublicKey() {
        return this.intraUserLoggedInPublicKey;
    }

    /**
     * Return the public key of intra user actor to add
     * @return String intra User To Add PublicKey
     */
    public String getIntraUserToAddPublicKey() {
        return this.intraUserToAddPublicKey;
    }
}
