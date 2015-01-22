package com.bitdubai.smartwallet.platform.layer._10_module;

/**
 * Created by ciencias on 21.01.15.
 */
public interface ModuleSubsystem {
    public void start () throws CantStartSubsystemException;
    public Module getModule();
}
