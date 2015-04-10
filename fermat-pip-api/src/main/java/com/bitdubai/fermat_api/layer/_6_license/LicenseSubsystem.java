package com.bitdubai.fermat_api.layer._6_license;


import com.bitdubai.fermat_api.Addon;

/**
 * Created by ciencias on 21.01.15.
 */
public interface LicenseSubsystem {
    public void start () throws CantStartSubsystemException;
    public Addon getAddon();
}
