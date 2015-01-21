package com.bitdubai.smartwallet.platform.layer._3_license;



/**
 * Created by ciencias on 21.01.15.
 */
public interface LicenseSubsystem {
    public void start () throws CantStartSubsystemException;
    public LicenseManager getLicenseManager();
}
