package com.bitdubai.fermat_api.layer.modules.common_classes;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

import org.apache.commons.lang.Validate;

/**
 * The class <code>com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation</code>
 * represents an actor identity with all the basic information.
 *
 * An Actor Identity Information contains all the basic information of an actor identity.
 *
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ActiveActorIdentityInformation {

    private final String publicKey;
    private final Actors actorType;
    private final String alias    ;
    private final byte[] image    ;

    public ActiveActorIdentityInformation(final String publicKey,
                                          final Actors actorType,
                                          final String alias,
                                          final byte[] image) {

        Validate.notNull(publicKey, "The Public Key can't be null.");
        Validate.notNull(actorType, "The Actor Type can't be null.");
        Validate.notNull(alias    , "The alias can't be null."     );
        Validate.notNull(image    , "The image can't be null."     );

        this.publicKey = publicKey;
        this.actorType = actorType;
        this.alias     = alias    ;
        this.image     = image    ;
    }

    /**
     * @return a string representing the public key.
     */
    public final String getPublicKey() {
        return publicKey;
    }

    /**
     * @return an element of Actors enum representing the type of the actor identity.
     */
    public final Actors getActorType() {
        return actorType;
    }

    /**
     * @return a string with the actor identity alias.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @return a byte array with the actor identity profile image.
     */
    public byte[] getImage() {
        return image;
    }

}
