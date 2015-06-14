package com.bitdubai.fermat_api.layer._3_platform_service;

import com.bitdubai.fermat_api.Addon;

/**
 * Created by ciencias on 23.01.15.
 */
public interface PlatformServiceSubsystem {
    public void start () throws CantStartSubsystemException;
    public Addon getAddon();
}


