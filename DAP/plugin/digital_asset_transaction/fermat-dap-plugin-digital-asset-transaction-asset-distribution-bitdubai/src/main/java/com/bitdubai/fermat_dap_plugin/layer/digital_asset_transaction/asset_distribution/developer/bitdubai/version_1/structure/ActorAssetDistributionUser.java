package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/10/15.
 */
public class ActorAssetDistributionUser implements ActorAssetUser {

    private String name;
    private String publicKey;
    private byte[] profileImage;
    private long registrationDate;
    private ConnectionState contactState;

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(String publicKey){
        this.publicKey=publicKey;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }

    @Override
    public long getContactRegistrationDate() {
        return this.registrationDate;
    }

    public void setContactRegistrationDate(long contactRegistrationDate){
        this.registrationDate=contactRegistrationDate;
    }

    @Override
    public byte[] getProfileImage() {
        return this.profileImage;
    }

    public void setProfileImage(byte[] profileImage){
        this.profileImage=profileImage;
    }

    @Override
    public ConnectionState getContactState() {
        return this.contactState;
    }

    public void setContactState(ConnectionState contactState){
        this.contactState=contactState;
    }

    public void setActorAssetUser(ActorAssetUser actorAssetUser){
        setContactRegistrationDate(actorAssetUser.getContactRegistrationDate());
        setContactState(actorAssetUser.getContactState());
        setName(actorAssetUser.getName());
        setProfileImage(actorAssetUser.getProfileImage());
        setPublicKey(actorAssetUser.getPublicKey());
    }

}
