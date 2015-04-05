package com.bitdubai.fermat_core.layer._4_hardware;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._4_hardware.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._4_hardware.HardwareSubsystem;
import com.bitdubai.fermat_core.layer._4_hardware.local_device.LocalDeviceHardwareSubsystem;
import com.bitdubai.fermat_pip_addon.layer._4_hardware.remote_device.remote_device.RemoteDeviceHardwareSubsystem;

/**
 * Created by ciencias on 2/24/15.
 */
public class HardwareLayer implements PlatformLayer{
    
    Addon mLocalDeviceManager;
    Addon mRemoteDeviceManager;





    public Addon getLocalDeviceManager(){
        return mLocalDeviceManager;        
    }
    
    public Addon getRemoteDeviceManager(){
        return mRemoteDeviceManager;        
    }





    @Override
    public void start() throws CantStartLayerException {


        /**
         * Let's start the Hardware Local Device Manager.
         * */

        HardwareSubsystem localDeviceSubsystem = new LocalDeviceHardwareSubsystem();
        
        try {
            localDeviceSubsystem.start();
            mLocalDeviceManager = localDeviceSubsystem.getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());
            
            throw new CantStartLayerException();
        }


        /**
         *Let's start the Hardware Remote Device Manager. 
         */
        
        HardwareSubsystem remoteDeviceSubsystem = new RemoteDeviceHardwareSubsystem();
        
        try {
            remoteDeviceSubsystem.start();
            mRemoteDeviceManager = remoteDeviceSubsystem.getAddon();

        }catch (CantStartSubsystemException e){
            System.err.println("CantStartSubsystemException: " + e.getMessage());
            
            throw new CantStartLayerException();
        }
    }
}
