package com.bitdubai.fermat_api.layer.dmp_actor.extra_user;


import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantCreateExtraUserRegistry;
import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantGetExtraUserRegistry;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;

import java.util.UUID;

/**
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer.identity.extra_user.UserRegistry</code> is a interface
 *     that define the methods for management the Users operations.
 *
 * Created by ciencias on 3/18/15.
 */
public interface UserRegistry {

    public Actor createUser(String userName) throws CantCreateExtraUserRegistry;

    public Actor createUser(String userName, byte[] photo) throws CantCreateExtraUserRegistry;

    public Actor getUser(UUID userId) throws CantGetExtraUserRegistry;

    public byte[] getPhoto(UUID id) throws CantGetExtraUserRegistry;

    public void setPhoto(UUID id, byte[] photo) throws CantGetExtraUserRegistry;
}
