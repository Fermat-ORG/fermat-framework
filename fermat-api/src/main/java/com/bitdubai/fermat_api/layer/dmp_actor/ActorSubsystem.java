package com.bitdubai.fermat_api.layer.dmp_actor;

import com.bitdubai.fermat_api.Plugin;


/**
 * Created by natalia on 11/08/15.
 */
public interface ActorSubsystem {

    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
