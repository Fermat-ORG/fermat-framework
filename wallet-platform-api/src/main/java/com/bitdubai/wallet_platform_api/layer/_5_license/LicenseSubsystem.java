package com.bitdubai.wallet_platform_api.layer._5_license;


import com.bitdubai.wallet_platform_api.Addon;

/**
 * Created by ciencias on 21.01.15.
 */
public interface LicenseSubsystem {
    public void start () throws CantStartSubsystemException;
    public Addon getAddon();
}
