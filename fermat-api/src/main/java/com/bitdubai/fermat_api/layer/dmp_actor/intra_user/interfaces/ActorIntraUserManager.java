package com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantAcceptIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDecideAcceptanceLaterException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDeleteIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantGetIntraUSersException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager</code>
 * defines the methods to administrate the intra users,
 */
public interface ActorIntraUserManager {
    
    void askForAcceptanceIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantCreateIntraUserException;

    void acceptIntraUser(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantAcceptIntraUserException;

    void decideAcceptanceLater(String intraUserLoggedInPublicKey, String intraUserToAddName, String intraUserToAddPublicKey, byte[] profileImage) throws CantDecideAcceptanceLaterException;

    void deleteIntraUSer(String intraUserLoggedInPublicKey, String intraUserToRemovePublicKey) throws CantDeleteIntraUserException;

    ActorIntraUser getAllIntraUsers(String intraUserLoggedInPublicKey) throws CantGetIntraUSersException;
}
