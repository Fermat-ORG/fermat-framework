package com.bitdubai.fermat_core.layer.pip_platform_service;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_pip_api.layer.platform_service.CantStartSubsystemException;
import com.bitdubai.fermat_pip_api.layer.platform_service.PlatformServiceSubsystem;
import com.bitdubai.fermat_core.layer.pip_platform_service.error_manager.ErrorManagerSubsystem;
import com.bitdubai.fermat_core.layer.pip_platform_service.event_manager.EventManagerSubsystem;
import com.bitdubai.fermat_core.layer.pip_platform_service.location_subsystem.LocationSubsystemSubsystem;
import com.bitdubai.fermat_core.layer.pip_platform_service.platform_info.PlatformInfoSubsystem;
/**
 * Created by ciencias on 23.01.15.
 */
public class PlatformServiceLayer implements PlatformLayer {

    Addon errorManager;
    Addon eventManager;
    Addon locationSubsystem;
    Addon platformInfo;



    public Addon getErrorManager() {
        return this.errorManager;
    }

    public Addon getEventManager() {
        return this.eventManager;
    }

    public Addon getLocationSubsystem(){
        return this.locationSubsystem;
    }

    public Addon getPlatformInfo(){
        return this.platformInfo;
    }


    @Override
    public void start() throws CantStartLayerException {


        /**
         * I will start the event Manager.
         */
        PlatformServiceSubsystem eventManagerSubsystem = new EventManagerSubsystem();

        try {
            eventManagerSubsystem.start();
            this.eventManager = ((PlatformServiceSubsystem) eventManagerSubsystem).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }

        /**
         * I will start the error Manager.
         */
        PlatformServiceSubsystem errorManagerSubsystem = new ErrorManagerSubsystem();

        try {
            errorManagerSubsystem.start();
            this.errorManager = ((PlatformServiceSubsystem) errorManagerSubsystem).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }

        /**
         * I will start the Location Subsystem.
         */

        PlatformServiceSubsystem locationSubsystemSubsystem = new LocationSubsystemSubsystem();

        try {
            locationSubsystemSubsystem.start();
            this.locationSubsystem = ((PlatformServiceSubsystem) locationSubsystemSubsystem).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CanStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }

        /**
         * I will start the Platform Info.
         */

        PlatformServiceSubsystem platformInfo = new PlatformInfoSubsystem();

        try {
            platformInfo.start();
            this.platformInfo = ((PlatformServiceSubsystem) platformInfo).getAddon();

        } catch (CantStartSubsystemException e) {
            System.err.println("CanStartSubsystemException: " + e.getMessage());

            throw new CantStartLayerException();
        }

    }


}
