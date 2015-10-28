package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by Matias Furszyfer on 2015.09.03..
 */
public class IncomingActorRequestConnectionNotificationEvent extends AbstractFermatEvent {


    private String actorId;
    private Actors actorType;
    private String actorName;
    private byte[] profileImage;


    public IncomingActorRequestConnectionNotificationEvent(EventType eventType, String actorId, Actors actorType,String actorName,byte[] profileImage) {
        super(eventType);
        this.actorId = actorId;
        this.actorType = actorType;
        this.actorName = actorName;
        this.profileImage = profileImage;
    }

    public IncomingActorRequestConnectionNotificationEvent(EventType eventType) {
        super(eventType);
    }


    public String getActorName() {
        return actorName;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public Actors getActorType() {
        return actorType;
    }

    public void setActorType(Actors actorType) {
        this.actorType = actorType;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
