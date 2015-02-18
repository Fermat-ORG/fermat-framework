package com.bitdubai.wallet_platform_api.layer._4_user;

import com.bitdubai.wallet_platform_api.Addon;

/**
 * Created by ciencias on 22.01.15.
 */
public interface UserSubsystem {
    public void start () throws CantStartSubsystemException;
    public Addon getAddon();
}
