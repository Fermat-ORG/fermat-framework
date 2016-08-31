package com.bitdubai.fermat_api.layer.modules.common_classes;

import com.bitdubai.fermat_api.layer.actor_connection.common.interfaces.ActorIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import java.io.Serializable;

/**
 * The class <code>com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation</code>
 * represents an actor identity with all the basic information.
 * <p/>
 * An Actor Identity Information contains all the basic information of an actor identity.
 * <p/>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface ActiveActorIdentityInformation extends Serializable, ActorIdentity {

    /**
     * @return a string with the actor identity alias.
     */
    String getAlias();

    /**
     * @return a byte array with the actor identity profile image.
     */
    byte[] getImage();

}
