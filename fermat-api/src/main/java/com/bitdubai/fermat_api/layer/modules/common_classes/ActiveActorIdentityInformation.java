package com.bitdubai.fermat_api.layer.modules.common_classes;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;

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


    /**
     * @return a string representing the public key.
     */
    public String getPublicKey();

    /**
     * @return an element of Actors enum representing the type of the actor identity.
     */
    public Actors getActorType();

    /**
     * @return a string with the actor identity alias.
     */
    public String getAlias();

    /**
     * @return a byte array with the actor identity profile image.
     */
    public byte[] getImage();

}
