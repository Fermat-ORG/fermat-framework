package com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSetPhotoException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;

/**
 * The Class <code>ExtraUserManager</code>
 * indicates the functionality of a ExtraUserManager
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface ExtraUserManager extends FermatManager {

    /**
     * Throw the method <code>createActor</code> we can create a new user (this, without picture).
     *
     * @param actorName assigned to the new actor
     * @return an instance of the new actor created.
     * @throws CantCreateExtraUserException if something goes wrong
     */
    Actor createActor(String actorName) throws CantCreateExtraUserException;

    /**
     * Throw the method <code>createActor</code> we can create a new user with a picture of him
     *
     * @param actorName assigned to the new actor
     * @param photo assigned to the new actor
     * @return an instance of the new actor created.
     * @throws CantCreateExtraUserException if something goes wrong
     */
    Actor createActor(String actorName,
                      byte[] photo) throws CantCreateExtraUserException;

    /**
     * Throw the method <code>getActor</code> we can get an instance of a actor previously created,
     *
     * @param actorPublicKey of the extra user i need to find.
     * @return an instance of the actor we're looking for
     * @throws CantGetExtraUserException if something goes wrong
     * @throws ExtraUserNotFoundException if i can't find the extra user
     */
    Actor getActorByPublicKey(String actorPublicKey) throws CantGetExtraUserException, ExtraUserNotFoundException;

    /**
     * set a photo to an existent extra-user
     *
     * @param actorPublicKey public key of the actor whom i want to change the photo
     * @param photo new photo for the user
     * @throws CantSetPhotoException if something goes wrong
     * @throws ExtraUserNotFoundException if i can't find the extra user
     */
    void setPhoto(String actorPublicKey, byte[] photo) throws CantSetPhotoException, ExtraUserNotFoundException;

}
