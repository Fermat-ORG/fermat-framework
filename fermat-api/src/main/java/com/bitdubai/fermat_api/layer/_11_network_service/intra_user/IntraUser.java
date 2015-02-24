package com.bitdubai.fermat_api.layer._11_network_service.intra_user;

import java.util.UUID;

/**
 * Created by ciencias on 2/13/15.
 */
public interface IntraUser {

    public UUID getUserId ();
    public String getUserName ();
    public int getLastLocation ();
    public IntraUserStatus getStatus ();
    public int getSmallProfilePicture ();
    public int getMediumProfilePicture ();
    public int getBigProfilePicture ();
    
    // Luis TODO: Datatypes must be changed
}
