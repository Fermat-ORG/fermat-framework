package com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUser;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.structure.IntraUserActorActor</code>
 * is the implementation of ActorIntraUser interface to provides the methods to consult the information of an Intra User <p/>
 *
 * Created by Created by natalia on 11/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public class IntraUserActorActor implements ActorIntraUser {

    /**
     * Gives us the public key of the represented intra user
     *
     * @return  A String with the public key
     */
    @Override
    public String getPublicKey() {
        return null;
    }

    /**
     * Gives us the name of the represented intra user
     *
     * @return A String with the name of the intra user
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Gives us the date when both intra users
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    @Override
    public long getContactRegistrationDate() {
        return 0;
    }

    /**
     * Gives us the profile image of the represented intra user
     *
     * @return the image
     */
    @Override
    public byte[] getProfileImage() {
        return new byte[0];
    }

    /**
     * Gives us the contact state of the represented intra
     * user
     *
     * @return the contact state
     */

    @Override
    public ContactState getContactState() {
        return null;
    }
}
