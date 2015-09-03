package com.bitdubai.fermat_api.layer.dmp_actor.extra_user;



import com.bitdubai.fermat_api.layer.dmp_actor.Actor;

import java.util.UUID;

/**
 *    <p>The abstract class <code>com.bitdubai.fermat_api.layer.identity.extra_user.ExtraUserManager</code> is a interface
 *     that define the methods for management the Users operations.
 *
 * Created by loui on 22/02/15.
 */
public interface ExtraUserManager {

    // TODO ADD EXCEPTIONS?

    Actor getActor(String actorPublicKey);

    void setPhoto(String actorPublicKey, byte[] photo);

    byte[] getPhoto(String actorPublicKey);

    Actor createActor(String userName);

    Actor createActor(String userName, byte[] photo);

}
