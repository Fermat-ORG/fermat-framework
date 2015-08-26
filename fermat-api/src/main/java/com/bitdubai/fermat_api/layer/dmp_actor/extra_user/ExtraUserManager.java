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

    public Actor getActor(UUID id);

    public void setPhoto(UUID id, byte[] photo);

    public byte[] getPhoto(UUID id);

    public Actor createActor(String userName);

    public Actor createActor(String userName, byte[] photo);

}
