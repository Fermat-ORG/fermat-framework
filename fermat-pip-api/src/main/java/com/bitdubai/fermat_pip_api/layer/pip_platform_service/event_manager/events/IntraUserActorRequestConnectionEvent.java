package com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events;

import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;

/**
 * Created by natalia on 17/08/15.
 */
public class IntraUserActorRequestConnectionEvent extends AbstractFermatEvent {

    private  String intraUserLoggedInPublicKey;
    private String intraUserToAddPublicKey;
    private String intraUserToAddName;
    private byte[] profileImage;

    public IntraUserActorRequestConnectionEvent(EventType eventType){
        super(eventType);
    }



    public void setIntraUserLoggedInPublicKey(String intraUserLoggedInPublicKey) {
       this.intraUserLoggedInPublicKey = intraUserLoggedInPublicKey;
    }

    public void setIntraUserToAddPublicKey(String intraUserToAddPublicKey) {
        this.intraUserToAddPublicKey = intraUserToAddPublicKey;
    }

    public void setIntraUserToAddName(String intraUserToAddName) {
        this.intraUserToAddName = intraUserToAddName;
    }

    public void setIntraUserProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
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

    /**
     *Return the name of intra user actor to add
     * @return String name
     */
    public String getIntraUserToAddName() {
        return this.intraUserToAddName;
    }

    /**
     * Return the profile image of intra user actor to add
     * @return byte image
     */
    public byte[] getIntraUserProfileImage() {
        return this.profileImage;
    }
}
