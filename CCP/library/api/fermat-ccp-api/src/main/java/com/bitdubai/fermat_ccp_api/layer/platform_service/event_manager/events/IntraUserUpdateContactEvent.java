package com.bitdubai.fermat_ccp_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.all_definition.events.AbstractCCPEvent;

/**
 * Created by Gian Barboza on 30/08/16.
 */
public class IntraUserUpdateContactEvent extends AbstractCCPEvent {

    private String identityPublicKey;
    private String identityAlias;
    private String identityPhrase;
    private byte[] profileImage;
    private Long accuracy;
    private Frequency frequency;
    private Location location;

    public IntraUserUpdateContactEvent(FermatEventEnum eventType) {
        super(eventType);
    }

    public void setIdentityPublicKey(String identityPublicKey){this.identityPublicKey = identityPublicKey;}

    public void setIdentityAlias(String identityAlias){this.identityAlias = identityAlias;}

    public void setIdentityPhrase(String identityPhrase){this.identityPhrase = identityPhrase;}

    public void setProfileImage(byte[] profileImage){this.profileImage = profileImage;}

    public void setAccuracy(Long accuracy){this.accuracy = accuracy;}

    public void setFrequency(Frequency frequency){this.frequency = frequency;}

    public void setLocation(Location location){this.location = location;}

    public String getIdentityPublicKey(){return identityPublicKey;}

    public String getIdentityAlias(){return identityAlias;}

    public String getIdentityPhrase(){return identityPhrase;}

    public byte[] getProfileImage(){return profileImage;}

    public Long getAccuracy(){return accuracy;}

    public Frequency getFrequency(){return frequency;}

    public Location getLocation(){return location;}
}
