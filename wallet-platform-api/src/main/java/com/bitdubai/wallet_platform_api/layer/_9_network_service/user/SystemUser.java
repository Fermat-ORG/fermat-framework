package com.bitdubai.wallet_platform_api.layer._9_network_service.user;

import java.util.UUID;

/**
 * Created by ciencias on 2/13/15.
 */
public interface SystemUser {

    public UUID getUserId ();
    public String getUserName ();
    public int getLastLocation ();
    public SystemUserStatus getStatus ();
    public int getSmallProfilePicture ();
    public int getMediumProfilePicture ();
    public int getBigProfilePicture ();
    
    // Luis TODO: Datatypes must be changed
}
