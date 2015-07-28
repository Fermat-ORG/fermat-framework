package com.bitdubai.fermat_pip_api.layer.pip_user;

import com.bitdubai.fermat_api.Addon;

/**
 * Created by ciencias on 22.01.15.
 */
public interface UserSubsystem {

    void start () throws CantStartSubsystemException;

    Addon getAddon();

}
