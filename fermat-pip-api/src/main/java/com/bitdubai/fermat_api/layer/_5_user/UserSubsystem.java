package com.bitdubai.fermat_api.layer._5_user;

import com.bitdubai.fermat_api.Addon;

/**
 * Created by ciencias on 22.01.15.
 */
public interface UserSubsystem {
    public void start () throws CantStartSubsystemException;
    public Addon getAddon();
}
