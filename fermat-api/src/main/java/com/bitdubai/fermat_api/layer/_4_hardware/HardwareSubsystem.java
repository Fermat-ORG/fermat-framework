package com.bitdubai.fermat_api.layer._4_hardware;

import com.bitdubai.fermat_api.Addon;

/**
 * Created by loui on 06/03/15.
 */
public interface HardwareSubsystem {
    public void start () throws CantStartSubsystemException;
    public Addon getAddon();
}
