package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.ccp_actor.intra_user.enums.ContactState;
import com.bitdubai.fermat_api.layer.ccp_actor.intra_user.interfaces.ActorIntraUser;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure.IntraUserActorActor</code>
 * is the implementation of ActorIntraUser interface to provides the methods to consult the information of an Intra User <p/>
 *
 * Created by Created by natalia on 11/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IntraUserActorActor implements ActorIntraUser {

    private String name;
    private String publicKey;
    private byte[] profileImage ;
    private long registrationDate;
    private ContactState contactState;

    /**
     * Constructor
     */
    public IntraUserActorActor(String name,String publicKey,byte[] profileImage,long registrationDate, ContactState contactState){

        this.name = name;
        this.publicKey = publicKey;
        this.profileImage = (byte[])profileImage.clone();
        this.registrationDate = registrationDate;
        this.contactState = contactState;

    }
    /**
     * Gives us the public key of the represented intra user
     *
     * @return  A String with the public key
     */
    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    /**
     * Gives us the name of the represented intra user
     *
     * @return A String with the name of the intra user
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gives us the date when both intra users
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    @Override
    public long getContactRegistrationDate() {
        return this.registrationDate;
    }

    /**
     * Gives us the profile image of the represented intra user
     *
     * @return the image
     */
    @Override
    public byte[] getProfileImage() {
        return (byte[])this.profileImage.clone();

    }

    /**
     * Gives us the contact state of the represented intra
     * user
     *
     * @return the contact state
     */

    @Override
    public ContactState getContactState() {
        return this.contactState;
    }
}
