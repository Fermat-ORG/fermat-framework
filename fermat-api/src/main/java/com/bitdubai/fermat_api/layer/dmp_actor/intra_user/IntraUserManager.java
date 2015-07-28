package com.bitdubai.fermat_api.layer.dmp_actor.intra_user;



import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.Actor;


import java.util.UUID;

/**
 * Created by loui on 22/02/15.
 */
public interface IntraUserManager {
    
    void createActor(String deviceUserPublicKey) throws CantCreateIntraUserException;

    Actor getActor(String deviceUserPublicKey);
}
